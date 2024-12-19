package com.aburilovic.springbootsandbox.repository;

import com.aburilovic.springbootsandbox.dto.TelemetryDataDTO;
import reactor.core.publisher.Flux;

public interface DeviceTelemetryRepository {
    Flux<TelemetryDataDTO> streamTelemetryData(String hardwareId);
}
