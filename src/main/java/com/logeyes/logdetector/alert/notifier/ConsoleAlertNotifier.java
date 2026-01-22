package com.logeyes.logdetector.alert.notifier;

import com.logeyes.logdetector.alert.event.AlertCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsoleAlertNotifier implements AlertNotifier{

    @Override
    public void notify(AlertCreatedEvent event) {
        log.warn("[ALERT-NOTIFY] service={}, severity={}, errorRate={}",
                event.getServiceName(),
                event.getSeverity(),
                event.getErrorRate());
    }
}
