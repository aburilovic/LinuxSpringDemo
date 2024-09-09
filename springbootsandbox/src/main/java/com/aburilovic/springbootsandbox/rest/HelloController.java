package com.aburilovic.springbootsandbox.rest;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/hello")
public class HelloController {

    final KafkaTemplate<String, String> template;

    public HelloController(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    @GetMapping("")
    public String hello() {
        return "Hello from Spring Boot!";
    }

    @GetMapping("/msg/{val}")
    public String messageKafka(@PathVariable("val") String value) {
        template.send("MyKafkaTopic", value);
        return value;
    }
}
