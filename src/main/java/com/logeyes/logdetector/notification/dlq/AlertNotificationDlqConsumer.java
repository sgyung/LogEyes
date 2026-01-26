package com.logeyes.logdetector.notification.dlq;

import com.logeyes.logdetector.alert.event.AlertNotificationFailedEvent;
import com.logeyes.logdetector.alert.notifier.EmailAlertNotifier;
import com.logeyes.logdetector.notification.domain.NotificationChannel;
import com.logeyes.logdetector.notification.service.AlertNotificationHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlertNotificationDlqConsumer {

    private final EmailAlertNotifier emailAlertNotifier;
    private final AlertNotificationHistoryService historyService;

    @KafkaListener(
            topics = "alert.notification.failed",
            groupId = "alert-notification-retry"
    )
    public void consume(AlertNotificationFailedEvent event) {

        log.warn(
                "[DLQ-CONSUME] retry email alertId={}, reason={}",
                event.getAlertId(),
                event.getErrorMessage()
        );

        try {
            // 이메일 재전송 시도
            emailAlertNotifier.retry(event.getAlertId());

            historyService.saveSuccess(
                    event.getAlertId(),
                    NotificationChannel.EMAIL
            );

            log.info(
                    "[DLQ-RETRY-SUCCESS] alertId={}",
                    event.getAlertId()
            );

        } catch (Exception e) {

            historyService.saveFail(
                    event.getAlertId(),
                    NotificationChannel.EMAIL,
                    e.getMessage()
            );

            log.error(
                    "[DLQ-RETRY-FAIL] alertId={}, reason={}",
                    event.getAlertId(),
                    e.getMessage()
            );
        }
    }
}
