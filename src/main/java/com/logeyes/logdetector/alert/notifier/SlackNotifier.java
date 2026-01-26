package com.logeyes.logdetector.alert.notifier;

import com.logeyes.logdetector.alert.domain.AlertSeverity;
import com.logeyes.logdetector.alert.event.AlertCreatedEvent;
import com.logeyes.logdetector.notification.domain.NotificationChannel;
import com.logeyes.logdetector.notification.result.NotificationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlackNotifier implements AlertNotifier {
    @Override
    public boolean support(AlertSeverity severity) {
        return severity == AlertSeverity.HIGH
                || severity == AlertSeverity.CRITICAL;
    }

    @Override
    public NotificationResult notify(AlertCreatedEvent event) {
        log.warn("[SLACK-ALERT] service={}, severity={}]", event.getServiceName(), event.getSeverity());

        return NotificationResult.success(channel());
    }

    @Override
    public NotificationChannel channel() {
        return NotificationChannel.SLACK;
    }
}
