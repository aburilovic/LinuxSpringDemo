package com.aburilovic.springbootsandbox.service;

import com.aburilovic.springbootsandbox.dto.TelemetryDataDTO;
import reactor.core.publisher.Flux;

public interface DeviceTelemetryService {
    Flux<TelemetryDataDTO> getTelemetryStream(String deviceId);
}
