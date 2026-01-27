package com.logeyes.logdetector.alert.consumer;

import com.logeyes.logdetector.alert.event.AlertResolvedEvent;
import com.logeyes.logdetector.alert.notifier.AlertNotifierRouter;
import com.logeyes.logdetector.notification.result.NotificationResult;
import com.logeyes.logdetector.notification.service.AlertNotificationHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlertResolvedConsumer {

    private final AlertNotifierRouter alertNotifierRouter;
    private final AlertNotificationHistoryService historyService;

    @KafkaListener(
            topics = "alert.resolved",
            groupId = "alert-recovery-notifier"
    )
    public void consume(AlertResolvedEvent event) {

        log.info(
                "[ALERT-RESOLVED-CONSUMED] alertId={}, service={}, fingerprint={}",
                event.getAlertId(),
                event.getServiceName(),
                event.getFingerprint()
        );

        // 복구 알림 전송
        List<NotificationResult> results =
                alertNotifierRouter.notifyRecovery(event);

        for (NotificationResult result : results) {

            if (result.isSuccess()) {

                log.info(
                        "[ALERT-RECOVERY-NOTIFY-SUCCESS] alertId={}, channel={}",
                        event.getAlertId(),
                        result.getChannel()
                );

                historyService.saveSuccess(
                        event.getAlertId(),
                        result.getChannel()
                );

            } else {

                log.warn(
                        "[ALERT-RECOVERY-NOTIFY-FAIL] alertId={}, channel={}, reason={}",
                        event.getAlertId(),
                        result.getChannel(),
                        result.getErrorMessage()
                );

                historyService.saveFail(
                        event.getAlertId(),
                        result.getChannel(),
                        result.getErrorMessage()
                );
            }
        }
    }
}