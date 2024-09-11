package com.aburilovic.springbootsandbox.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceDTO {
    private String hardwareId;
    private String sku;
    private String description;
}
