package com.logeyes.logdetector.common.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic alertCreatedDLT() {
        return TopicBuilder.name("alert.created.DLT")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
