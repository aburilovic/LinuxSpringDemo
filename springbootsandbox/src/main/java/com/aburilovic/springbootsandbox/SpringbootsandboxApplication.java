package com.aburilovic.springbootsandbox;

import com.aburilovic.springbootsandbox.entity.DeviceEntity;
import com.aburilovic.springbootsandbox.repository.DeviceRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class SpringbootsandboxApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootsandboxApplication.class, args);
    }

    @Bean
    public ApplicationRunner kafkaStartupRunner(KafkaTemplate<String, String> template) {
        return args -> {
            template.send("MyKafkaTopic", "test from Main app");
        };
    }

    @Bean
    public ApplicationRunner databaseStartupRunner(DeviceRepository deviceRepository) {
        return args -> {
            deviceRepository.save(DeviceEntity.builder()
                    .description("Regular device used in production")
                    .hardwareId("Samsung10")
                    .sku("SKU155")
                    .build());

            deviceRepository.save(DeviceEntity.builder()
                    .description("Regular device used just for testing")
                    .hardwareId("Samsung12")
                    .sku("SKU188")
                    .build());
        };
    }
}
