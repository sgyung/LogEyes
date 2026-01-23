package com.logeyes.logdetector.common.config.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    // 재시도 처리
    @Bean
    public DefaultErrorHandler kafkaErrorHandler() {

        DeadLetterPublishingRecoverer recoverer =
                new DeadLetterPublishingRecoverer(
                        kafkaTemplate,
                        (ConsumerRecord<?,?> record, Exception ex) -> {
                            String dlqTopic = record.topic() + ".dlq";
                            log.error("[DLQ-ROUTE] topic={} -> {}, partition={}, offset={}, key={}",
                                    record.topic(), dlqTopic, record.partition(), record.offset(), record.key(), ex);
                            return new org.apache.kafka.common.TopicPartition(dlqTopic, record.partition());
                        });

        // 재시도: 3번, 간격: 1000ms (원하면 500/2000 등으로 조절)
        FixedBackOff backOff = new FixedBackOff(1000L, 3L);

        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, backOff);

        // 역직렬화(Deserialize) 실패는 보통 재시도 의미가 없어서 바로 DLQ로 보내는 게 안전
        errorHandler.addNotRetryableExceptions(
                DeserializationException.class,
                IllegalArgumentException.class
        );

        return errorHandler;
    }
}
