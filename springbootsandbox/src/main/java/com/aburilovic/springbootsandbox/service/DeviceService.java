package com.aburilovic.springbootsandbox.service;

import com.aburilovic.springbootsandbox.dto.DeviceDTO;

import java.util.List;

public interface DeviceService {
    List<DeviceDTO> getAllDevices();
    DeviceDTO getDeviceById(Long id);
    DeviceDTO getDeviceByHardwareId(String hardwareId);
}
