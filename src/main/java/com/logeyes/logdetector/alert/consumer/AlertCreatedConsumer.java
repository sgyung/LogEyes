package com.logeyes.logdetector.alert.consumer;

import com.logeyes.logdetector.alert.event.AlertCreatedEvent;
import com.logeyes.logdetector.alert.event.AlertNotificationFailedEvent;
import com.logeyes.logdetector.alert.notifier.AlertNotifier;
import com.logeyes.logdetector.alert.notifier.AlertNotifierRouter;
import com.logeyes.logdetector.common.config.kafka.AlertNotificationDlqPublisher;
import com.logeyes.logdetector.notification.domain.NotificationChannel;
import com.logeyes.logdetector.notification.result.NotificationResult;
import com.logeyes.logdetector.notification.service.AlertNotificationHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlertCreatedConsumer {

    private final AlertNotifierRouter alertNotifierRouter;
    private final AlertNotificationHistoryService historyService;
    private final AlertNotificationDlqPublisher dlqPublisher;

    @KafkaListener(
            topics = "alert.created",
            groupId = "alert-notifier",
            containerFactory = "alertCreatedKafkaListenerContainerFactory"
    )
    public void consume(AlertCreatedEvent event){
        log.info("[ALERT-CONSUMED] id={}, service={}, severity={}",
                event.getAlertId(),
                event.getServiceName(),
                event.getSeverity());

        // 알림 전송
        List<NotificationResult> results = alertNotifierRouter.notify(event);

        // 알림 이력 저장
        for(NotificationResult result : results) {

            if (result.isSuccess()) {

                log.info(
                        "[ALERT-NOTIFY-SUCCESS] alertId={}, channel={}",
                        event.getAlertId(),
                        result.getChannel()
                );

                historyService.saveSuccess(event.getAlertId(), result.getChannel());
            } else {

                log.warn(
                        "[ALERT-NOTIFY-FAIL] alertId={}, channel={}, reason={}",
                        event.getAlertId(),
                        result.getChannel(),
                        result.getErrorMessage()
                );

                historyService.saveFail(event.getAlertId(), result.getChannel(), result.getErrorMessage());
            }


            // EMAIL 실패 DLQ 로그
            if (result.getChannel() == NotificationChannel.EMAIL
                    && !result.isSuccess()) {

                log.error(
                        "[ALERT-NOTIFY-DLQ] EMAIL failed → DLQ alertId={}, reason={}",
                        event.getAlertId(),
                        result.getErrorMessage()
                );

                dlqPublisher.publish(
                        new AlertNotificationFailedEvent(
                                event.getAlertId(),
                                result.getChannel(),
                                result.getErrorMessage(),
                                LocalDateTime.now()
                        )
                );
            }
        }
    }
}
