package com.logeyes.logdetector.alert.consumer;

import com.logeyes.logdetector.alert.event.AlertCreatedEvent;
import com.logeyes.logdetector.alert.notifier.AlertNotifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlertCreatedConsumer {

    private final AlertNotifier alertNotifier;

    @KafkaListener(
            topics = "alert.created",
            groupId = "alert-notifier"
    )
    public void consume(AlertCreatedEvent event){
        log.info("[ALERT-CONSUMED] id={}, service={}, severity={}",
                event.getAlertId(),
                event.getServiceName(),
                event.getSeverity());

        alertNotifier.notify(event);
    }
}
