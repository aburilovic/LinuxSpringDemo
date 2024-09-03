package com.aburilovic.springbootsandbox;

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
	public ApplicationRunner runner(KafkaTemplate<String, String> template) {
		return args -> {
			template.send("GovnoTopic", "test from Main app");
		};
	}

}
