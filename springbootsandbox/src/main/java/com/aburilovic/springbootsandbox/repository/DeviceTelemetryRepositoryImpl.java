package com.aburilovic.springbootsandbox.repository;

import com.aburilovic.springbootsandbox.dto.TelemetryDataDTO;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * In a real app, this would connect to a reactive data source, like a WebSocket, Kafka, or a reactive database.
 */
@Repository
public class DeviceTelemetryRepositoryImpl implements DeviceTelemetryRepository {

    // Simulate fetching a reactive stream of telemetry data
    public Flux<TelemetryDataDTO> streamTelemetryData(String hardwareId) {
        return Flux.interval(Duration.ofMillis(500)) // Simulate continuous data
                .map(i -> TelemetryDataDTO.builder()
                        .hardwareId(hardwareId)
                        .metricName("Metric " + i)
                        .value(Math.random() * 100)
                        .build())
                .onBackpressureDrop();
    }
}
