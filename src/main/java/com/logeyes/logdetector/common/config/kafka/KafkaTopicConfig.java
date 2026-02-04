package com.logeyes.logdetector.common.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic rawLogTopic() {
        return TopicBuilder.name("raw.log")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic alertCreatedTopic() {
        return TopicBuilder.name("alert.created")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic alertResolvedTopic() {
        return TopicBuilder.name("alert.resolved")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic alertNotificationFailedTopic() {
        return TopicBuilder.name("alert.notification.failed")
                .partitions(1)
                .replicas(1)
                .build();
    }

    // DLQ 토픽 (컨슈머에서 .dlq 쓰고 있으니까 소문자로 통일)
    @Bean
    public NewTopic rawLogDlqTopic() {
        return TopicBuilder.name("raw.log.dlq")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic alertCreatedDlqTopic() {
        return TopicBuilder.name("alert.created.dlq")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
