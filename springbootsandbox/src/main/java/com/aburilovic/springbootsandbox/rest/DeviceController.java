package com.aburilovic.springbootsandbox.rest;

import com.aburilovic.springbootsandbox.dto.DeviceDTO;
import com.aburilovic.springbootsandbox.entity.DeviceEntity;
import com.aburilovic.springbootsandbox.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/hardwareId/{hardwareId}")
    public ResponseEntity<DeviceDTO> getAllDeviceByHardwareId(@PathVariable("hardwareId") String hardwareId) {
        final DeviceDTO device = deviceService.getDeviceByHardwareId(hardwareId);
        if (device == null) {
            // Return 204 No Content if the list is empty
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        // Return 200 OK with the list of devices
        return ResponseEntity.ok(device);
    }

    @PostMapping("/device")
    public ResponseEntity<DeviceDTO> createDevice(@RequestBody DeviceDTO deviceDTO) {
        DeviceEntity createdDevice = deviceService.createDevice(deviceDTO);

        if (createdDevice == null) {
            // Return 500 Internal Server Error if the creation fails
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Return 201 Created with the created device data
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceDTO);
    }
}
