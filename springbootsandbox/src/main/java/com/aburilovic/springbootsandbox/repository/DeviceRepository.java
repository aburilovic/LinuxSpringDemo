package com.aburilovic.springbootsandbox.repository;

import com.aburilovic.springbootsandbox.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {
    Optional<DeviceEntity> findByHardwareId(String hardwareId);
    Optional<DeviceEntity> findBySku(String sku);
}
