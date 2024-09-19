package com.aburilovic.springbootsandbox.service;

import com.aburilovic.springbootsandbox.dto.DeviceDTO;
import com.aburilovic.springbootsandbox.entity.DeviceEntity;
import com.aburilovic.springbootsandbox.mapper.DeviceMapper;
import com.aburilovic.springbootsandbox.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    @Override
    public List<DeviceDTO> getAllDevices() {
        return DeviceMapper.convertToDTOList(deviceRepository.findAll());
    }

    @Override
    public DeviceDTO getDeviceById(Long id) {
        final Optional<DeviceEntity> deviceEntity = deviceRepository.findById(id);
        return DeviceMapper.convertToDTO(deviceEntity.orElse(null));
    }

    @Override
    public DeviceDTO getDeviceByHardwareId(String hardwareId) {
        final Optional<DeviceEntity> deviceEntity = deviceRepository.findByHardwareId(hardwareId);
        return DeviceMapper.convertToDTO(deviceEntity.orElse(null));
    }

    @Override
    public DeviceDTO getDeviceBySku(String sku) {
        final Optional<DeviceEntity> deviceEntity = deviceRepository.findBySku(sku);
        return DeviceMapper.convertToDTO(deviceEntity.orElse(null));
    }

    @Override
    public DeviceEntity getDeviceEntityBySku(String sku) {
        final Optional<DeviceEntity> deviceEntity = deviceRepository.findBySku(sku);
        return deviceEntity.orElse(null);
    }

    @Override
    public DeviceEntity createDevice(DeviceDTO deviceDTO) {
        DeviceEntity deviceEntity = DeviceMapper.convertToEntity(deviceDTO);
        return deviceRepository.save(deviceEntity);
    }

    @Override
    public boolean deleteDeviceById(Long id) {
        if (deviceRepository.existsById(id)) {
            deviceRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
