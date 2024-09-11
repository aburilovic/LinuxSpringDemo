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
            DeviceEntity deviceEntity1 = new DeviceEntity();
            deviceEntity1.setHardwareId("Samsung10");
            deviceEntity1.setSku("SKU155");
            deviceEntity1.setDescription("Regular device used in production");
            deviceRepository.save(deviceEntity1);


            DeviceEntity deviceEntity2 = new DeviceEntity();
            deviceEntity2.setHardwareId("Samsung12");
            deviceEntity2.setSku("SKU188");
            deviceEntity2.setDescription("Regular device used only in testing");
            deviceRepository.save(deviceEntity2);
        };
    }

}
