package com.logeyes.logdetector.alert.notifier;

import com.logeyes.logdetector.alert.domain.AlertSeverity;
import com.logeyes.logdetector.alert.event.AlertCreatedEvent;
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
    public void notify(AlertCreatedEvent event) {
        log.info(
                "[EMAIL] CRITICAL ALERT service={}",
                event.getServiceName()
        );
    }
}
