package com.example.clientapp.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public KafkaAdmin.NewTopics createTopics() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("orders")
                        .partitions(2)
                        .build(),
                TopicBuilder.name("notification")
                        .partitions(2)
                        .build());
    }

//    @Bean
//    public NewTopic ordersTopic() {
//        return TopicBuilder.name("orders")
//                .partitions(2)
//                .build();
//    }
//
//    @Bean
//    public NewTopic notificationTopic() {
//        return TopicBuilder.name("notification")
//                .partitions(2)
//                .build();
//    }

}
