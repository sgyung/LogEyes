package com.logeyes.logdetector.common.config.kafka;

import com.logeyes.logdetector.alert.event.AlertCreatedEvent;
import com.logeyes.logdetector.alert.event.AlertEventPublisher;
import com.logeyes.logdetector.alert.event.AlertResolvedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaAlertEventPublisher implements AlertEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String CREATE_TOPIC = "alert.created";
    private static final String RESOLVE_TOPIC = "alert.resolved";

    @Override
    public void publishAlertCreated(AlertCreatedEvent event) {
        kafkaTemplate.send(CREATE_TOPIC, event);

        log.info("[KAFKA-PUBLISH] topic={}, alertId={}", CREATE_TOPIC, event.getAlertId());
    }

    @Override
    public void publishAlertResolved(AlertResolvedEvent event) {
        kafkaTemplate.send(RESOLVE_TOPIC, event);

        log.info("[KAFKA-PUBLISH] topic={}, alertId={}", RESOLVE_TOPIC, event.getAlertId());
    }


}
