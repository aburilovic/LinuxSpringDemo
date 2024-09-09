package com.aburilovic.springbootsandbox.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Profile("!dev")
public class KafkaConfig {

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("MyKafkaTopic")
                .build();
    }

    @KafkaListener(id = "myId", topics = "MyKafkaTopic")
    public void listen(String in) {
        System.out.println("***************** "+ in);
    }
}
