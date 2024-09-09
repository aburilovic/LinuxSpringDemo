package com.aburilovic.springbootsandbox.config.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.Nullable;
import org.springframework.util.concurrent.ListenableFuture;

public class DummyKafkaTemplate<K,V> extends KafkaTemplate<K,V> {
    public DummyKafkaTemplate(ProducerFactory<K, V>  producerFactory) {
        super(producerFactory);
    }

    @Override
    public ListenableFuture<SendResult<K, V>> send(String topic, @Nullable V data) {
        System.out.println("SENDING TO TOPIC: "+topic);
        System.out.println("DATA: "+data);
        return null;
    }
}
