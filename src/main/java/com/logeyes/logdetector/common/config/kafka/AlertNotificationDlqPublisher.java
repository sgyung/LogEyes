package com.logeyes.logdetector.common.config.kafka;

import com.logeyes.logdetector.alert.event.AlertNotificationFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlertNotificationDlqPublisher {

    private static final String DLQ_TOPIC = "alert.notification.failed";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(AlertNotificationFailedEvent event){

        kafkaTemplate.send(DLQ_TOPIC, event);

        log.error(
                "[ALERT-NOTIFICATION-DLQ] alertId={}, channel={}, reason={}",
                event.getAlertId(),
                event.getChannel(),
                event.getErrorMessage()
        );
    }
}
