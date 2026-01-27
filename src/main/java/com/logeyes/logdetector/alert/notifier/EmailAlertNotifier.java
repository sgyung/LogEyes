package com.logeyes.logdetector.alert.notifier;

import com.logeyes.logdetector.alert.domain.AlertSeverity;
import com.logeyes.logdetector.alert.event.AlertCreatedEvent;
import com.logeyes.logdetector.alert.event.AlertResolvedEvent;
import com.logeyes.logdetector.notification.domain.NotificationChannel;
import com.logeyes.logdetector.notification.result.NotificationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailAlertNotifier implements AlertNotifier {
    @Override
    public boolean support(AlertSeverity severity) {
        return severity == AlertSeverity.CRITICAL;
    }

    @Override
    public NotificationResult notify(AlertCreatedEvent event) {
        log.info(
                "[EMAIL] CRITICAL ALERT service={}",
                event.getServiceName()
        );

        return NotificationResult.success(channel());
    }

    @Override
    public NotificationChannel channel() {
        return NotificationChannel.EMAIL;
    }

    public void retry(Long alertId) {

        log.info(
                "[EMAIL-RETRY] retry alertId={}",
                alertId
        );

        // 실제 프로젝트에서는:
        // - alert 조회
        // - 메일 재전송
        // 지금은 구조용이므로 로그만
    }

    @Override
    public NotificationResult notifyRecovery(AlertResolvedEvent event) {

        log.info("[RECOVERY] service={}, fingerprint={}, resolvedAt={}",
                event.getServiceName(),
                event.getFingerprint(),
                event.getResolvedAt());

        return NotificationResult.success(channel());
    }
}
