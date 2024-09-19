package com.aburilovic.springbootsandbox.service;

import com.aburilovic.springbootsandbox.dto.DeviceDTO;
import com.aburilovic.springbootsandbox.entity.DeviceEntity;

import java.util.List;

public interface DeviceService {
    List<DeviceDTO> getAllDevices();
    DeviceDTO getDeviceById(Long id);
    DeviceDTO getDeviceByHardwareId(String hardwareId);

    DeviceEntity createDevice(DeviceDTO deviceDTO);
}
