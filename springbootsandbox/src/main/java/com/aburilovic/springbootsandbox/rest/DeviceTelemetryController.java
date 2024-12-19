package com.aburilovic.springbootsandbox.rest;

import com.aburilovic.springbootsandbox.dto.TelemetryDataDTO;
import com.aburilovic.springbootsandbox.service.DeviceTelemetryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/reactive/devices/telemetry")
@RequiredArgsConstructor
@Slf4j
public class DeviceTelemetryController {
    private final DeviceTelemetryService telemetryService;

    // Streaming device telemetry data
    @GetMapping(value = "/stream/{deviceId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TelemetryDataDTO> streamDeviceTelemetry(@PathVariable String deviceId) {
        return telemetryService.getTelemetryStream(deviceId);
    }
}
