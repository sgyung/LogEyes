package com.logeyes.logdetector.alert.notifier;

import com.logeyes.logdetector.alert.domain.AlertSeverity;
import com.logeyes.logdetector.alert.event.AlertCreatedEvent;
import com.logeyes.logdetector.notification.domain.NotificationChannel;
import com.logeyes.logdetector.notification.result.NotificationResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsoleAlertNotifier implements AlertNotifier{

    @Override
    public boolean support(AlertSeverity  severity) {
        return true;
    }

    @Override
    public NotificationResult notify(AlertCreatedEvent event) {
        log.warn("[CONSOLE-ALERT] service={}, severity={}, errorRate={}",
                event.getServiceName(),
                event.getSeverity(),
                event.getErrorRate());

        return NotificationResult.success(channel());
    }

    @Override
    public NotificationChannel channel() {
        return NotificationChannel.CONSOLE;
    }
}
