package com.aburilovic.springbootsandbox.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TelemetryDataDTO {
    private String hardwareId;
    private String metricName;
    private double value;
}
