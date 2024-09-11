package com.aburilovic.springbootsandbox.rest;

import com.aburilovic.springbootsandbox.dto.DeviceDTO;
import com.aburilovic.springbootsandbox.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;
    private final KafkaTemplate<String, String> template;

    @GetMapping("")
    public String hello() {
        return "Hello from Spring Boot Device Controller!";
    }

    @GetMapping("/msg/{val}")
    public String messageKafka(@PathVariable("val") String value) {
        template.send("MyKafkaTopic", value);
        return value;
    }

    @GetMapping("all")
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        final List<DeviceDTO> devices = deviceService.getAllDevices();
        if (devices.isEmpty()) {
            // Return 204 No Content if the list is empty
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        // Return 200 OK with the list of devices
        return ResponseEntity.ok(devices);
    }
}
