package com.aburilovic.springbootsandbox.config.kafka;

import com.aburilovic.springbootsandbox.config.kafka.DummyKafkaTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile("dev")
public class KafkaConfigDev {

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new DummyKafkaTemplate<>(mockProducerFactory());
    }

    @Bean
    public ProducerFactory<String, String> mockProducerFactory() {
        // Using a MockProducer from Kafka to simulate message sending without an actual Kafka broker
        return new DefaultKafkaProducerFactory<>(mockProducerConfigs());
    }

    private Map<String, Object> mockProducerConfigs() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put("bootstrap.servers", "mock:9092"); // A dummy value for the mock
        configProps.put("key.serializer", StringSerializer.class);
        configProps.put("value.serializer", StringSerializer.class);
        return configProps;
    }
}
