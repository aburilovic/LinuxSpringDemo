package com.aburilovic.springbootsandbox.service;

import com.aburilovic.springbootsandbox.dto.TelemetryDataDTO;
import com.aburilovic.springbootsandbox.repository.DeviceTelemetryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class DeviceTelemetryServiceImpl implements DeviceTelemetryService {
    private final DeviceTelemetryRepository telemetryRepository;

    // Simulate a stream of telemetry data
    public Flux<TelemetryDataDTO> getTelemetryStream(String deviceId) {
        return telemetryRepository.streamTelemetryData(deviceId)
                .delayElements(Duration.ofSeconds(1)); // Simulate data arriving every second
    }
}
