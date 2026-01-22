package com.logeyes.logdetector.common.config.kafka;

import com.logeyes.logdetector.alert.event.AlertCreatedEvent;
import com.logeyes.logdetector.alert.event.AlertEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaAlertEventPublisher implements AlertEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "alert.created";

    @Override
    public void publishAlertCreated(AlertCreatedEvent event) {
        kafkaTemplate.send(TOPIC, event);

        log.info("[KAFKA-PUBLISH] topic={}, alertId={}", TOPIC, event.getAlertId());
    }


}
