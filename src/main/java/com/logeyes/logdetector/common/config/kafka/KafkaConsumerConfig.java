package com.logeyes.logdetector.common.config.kafka;

import com.logeyes.logdetector.alert.event.AlertCreatedEvent;
import com.logeyes.logdetector.alert.event.AlertResolvedEvent;
import com.logeyes.logdetector.stat.rawlog.dto.RawLogEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableKafka
public class KafkaConsumerConfig {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    // ===========================
    // DLQ + Retry 공통 ErrorHandler
    // ===========================
    @Bean
    public DefaultErrorHandler kafkaErrorHandler() {

        DeadLetterPublishingRecoverer recoverer =
                new DeadLetterPublishingRecoverer(
                        kafkaTemplate,
                        (ConsumerRecord<?, ?> record, Exception ex) -> {
                            String dlqTopic = record.topic() + ".dlq";
                            log.error("[DLQ-ROUTE] topic={} -> {}, partition={}, offset={}, key={}",
                                    record.topic(), dlqTopic, record.partition(), record.offset(), record.key(), ex);
                            return new TopicPartition(dlqTopic, record.partition());
                        });

        FixedBackOff backOff = new FixedBackOff(1000L, 3L);

        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, backOff);
        errorHandler.addNotRetryableExceptions(
                DeserializationException.class,
                IllegalArgumentException.class
        );

        return errorHandler;
    }

    // ===========================
    // 공통 Consumer Props
    // ===========================
    private Map<String, Object> commonConsumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return props;
    }

    private <T> JsonDeserializer<T> jsonDeserializer(Class<T> targetClass) {
        JsonDeserializer<T> deserializer = new JsonDeserializer<>(targetClass);
        deserializer.addTrustedPackages("*");
        deserializer.setRemoveTypeHeaders(true);
        deserializer.setUseTypeHeaders(false);   // ⭐ 타입 헤더 충돌 방지 핵심
        deserializer.setUseTypeMapperForKey(false);
        return deserializer;
    }

    // ===========================
    // RawLog Consumer
    // ===========================
    @Bean
    public ConsumerFactory<String, RawLogEvent> rawLogConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                commonConsumerProps(),
                new StringDeserializer(),
                jsonDeserializer(RawLogEvent.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RawLogEvent>
    rawLogKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, RawLogEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(rawLogConsumerFactory());
        factory.setCommonErrorHandler(kafkaErrorHandler());
        return factory;
    }

    // ===========================
    // AlertCreated Consumer
    // ===========================
    @Bean
    public ConsumerFactory<String, AlertCreatedEvent> alertCreatedConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                commonConsumerProps(),
                new StringDeserializer(),
                jsonDeserializer(AlertCreatedEvent.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AlertCreatedEvent>
    alertCreatedKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, AlertCreatedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(alertCreatedConsumerFactory());
        factory.setCommonErrorHandler(kafkaErrorHandler());
        return factory;
    }

    // ===========================
    // AlertResolved Consumer
    // ===========================
    @Bean
    public ConsumerFactory<String, AlertResolvedEvent> alertResolvedConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                commonConsumerProps(),
                new StringDeserializer(),
                jsonDeserializer(AlertResolvedEvent.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AlertResolvedEvent>
    alertResolvedKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, AlertResolvedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(alertResolvedConsumerFactory());
        factory.setCommonErrorHandler(kafkaErrorHandler());
        return factory;
    }
}
