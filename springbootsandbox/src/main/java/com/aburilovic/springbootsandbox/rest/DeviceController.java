package com.aburilovic.springbootsandbox.rest;

import com.aburilovic.springbootsandbox.dto.DeviceDTO;
import com.aburilovic.springbootsandbox.entity.DeviceEntity;
import com.aburilovic.springbootsandbox.service.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/devices")
@RequiredArgsConstructor
@Slf4j
public class DeviceController {

    private final DeviceService deviceService;
    private final KafkaTemplate<String, String> template;

    @GetMapping("/home")
    public String home() {
        return "Hello from Spring Boot Device Controller!";
    }

    @GetMapping("/msg/{val}")
    public String messageKafka(@PathVariable("val") String value) {
        template.send("MyKafkaTopic", value);
        return value;
    }

    @PreAuthorize("hasRole('User')")
    @GetMapping("all")
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        log.info("Received request for /all");
        final List<DeviceDTO> devices = deviceService.getAllDevices();
        if (devices.isEmpty()) {
            // Return 204 No Content if the list is empty
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        // Return 200 OK with the list of devices
        return ResponseEntity.ok(devices);
    }

    @PreAuthorize("hasAnyRole('Admin', 'DeviceManager')")
    @GetMapping("/hardwareId/{hardwareId}")
    public ResponseEntity<DeviceDTO> getDeviceByHardwareId(@PathVariable("hardwareId") String hardwareId) {
        log.info("Received request for /hardwareId/{hardwareId} with parameter: {}", hardwareId);
        final DeviceDTO device = deviceService.getDeviceByHardwareId(hardwareId);
        if (device == null) {
            // Return 204 No Content if the list is empty
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        // Return 200 OK with the list of devices
        return ResponseEntity.ok(device);
    }

    @PreAuthorize("hasAnyRole('Admin', 'DeviceManager')")
    @GetMapping("/sku/{sku}")
    public ResponseEntity<DeviceDTO> getDeviceBySku(@PathVariable("sku") String sku) {
        log.info("Received request for /sku/{sku} with parameter: {}", sku);
        final DeviceDTO device = deviceService.getDeviceBySku(sku);
        if (device == null) {
            // Return 204 No Content if the list is empty
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        // Return 200 OK with the list of devices
        return ResponseEntity.ok(device);
    }

    /**
     * Only Admin can create device.
     * @param deviceDTO
     * @return
     */
    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/device")
    public ResponseEntity<DeviceDTO> createDevice(@RequestBody DeviceDTO deviceDTO) {
        log.info("Received POST request for /device with request body: {}", deviceDTO);
        DeviceEntity createdDevice = deviceService.createDevice(deviceDTO);

        if (createdDevice == null) {
            // Return 500 Internal Server Error if the creation fails
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Return 201 Created with the created device data
        return ResponseEntity.status(HttpStatus.CREATED).body(deviceDTO);
    }

    /**
     * Only device manager can delete the device.
     * @param sku
     * @return
     */
    @PreAuthorize("hasRole('DeviceManager')")
    @DeleteMapping("/device/sku/{sku}")
    public ResponseEntity<Void> deleteDevice(@PathVariable("sku") String sku) {
        log.info("Received DELETE request for /device/sku/{sku} with parameter: {}", sku);
        DeviceEntity deviceEntity = deviceService.getDeviceEntityBySku(sku);
        if (deviceEntity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        boolean isDeleted = deviceService.deleteDeviceById(deviceEntity.getId());

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
