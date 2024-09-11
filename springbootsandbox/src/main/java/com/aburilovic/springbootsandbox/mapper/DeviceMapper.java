package com.aburilovic.springbootsandbox.mapper;

import com.aburilovic.springbootsandbox.dto.DeviceDTO;
import com.aburilovic.springbootsandbox.entity.DeviceEntity;

import java.util.List;
import java.util.stream.Collectors;

public class DeviceMapper {

    /**
     * Static method to converted DeviceEntity to DeviceDTO
     *
     * @param entity - entity object to be converted to dto
     * @return
     */
    public static DeviceDTO convertToDTO(DeviceEntity entity) {
        if (entity == null) {
            return null;
        }

        return DeviceDTO.builder()
                .sku(entity.getSku())
                .hardwareId(entity.getHardwareId())
                .description(entity.getDescription())
                .build();
    }

    /**
     * Static method to converted DeviceDTO to DeviceEntity
     *
     * @param deviceDTO - dto object to be converted to entity
     * @return
     */
    public static DeviceEntity convertToEntity(DeviceDTO deviceDTO) {
        if (deviceDTO == null) {
            return null;
        }
        return DeviceEntity.builder()
                .description(deviceDTO.getDescription())
                .hardwareId(deviceDTO.getHardwareId())
                .sku(deviceDTO.getSku())
                .build();
    }

    /**
     * Static method to convert a list of entities to DTOs
     *
     * @param entities - list of entities to be converted to the list of DTOs
     * @return
     */
    public static List<DeviceDTO> convertToDTOList(List<DeviceEntity> entities) {
        return entities.stream()
                .map(DeviceMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}
