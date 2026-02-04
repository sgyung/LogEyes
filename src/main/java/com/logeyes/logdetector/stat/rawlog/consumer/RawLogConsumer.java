package com.logeyes.logdetector.stat.rawlog.consumer;

import com.logeyes.logdetector.stat.rawlog.dto.RawLogEvent;
import com.logeyes.logdetector.stat.rawlog.service.RawLogAggregationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RawLogConsumer {

    private final RawLogAggregationService aggregationService;

    @KafkaListener(
            topics = "raw.log",
            groupId = "log-stat-aggregator",
            containerFactory = "rawLogKafkaListenerContainerFactory"
    )
    public void consume(RawLogEvent event) {

        log.info("[RAW-LOG] service={}, env={}, level={}, fingerprint={}",
                event.getServiceName(),
                event.getEnvironment(),
                event.getLevel(),
                event.getFingerprint()
        );

        aggregationService.aggregate(event);
    }
}
