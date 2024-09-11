package com.aburilovic.springbootsandbox.mapper;

import com.aburilovic.springbootsandbox.dto.DeviceDTO;
import com.aburilovic.springbootsandbox.entity.DeviceEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DeviceMapperTest {

    @Test
    void convertToDTO() {
        // Given
        DeviceEntity entity = DeviceEntity.builder()
                .sku("123")
                .hardwareId("hardware-id")
                .description("A device description")
                .build();

        // When
        DeviceDTO dto = DeviceMapper.convertToDTO(entity);

        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getSku()).isEqualTo("123");
        assertThat(dto.getHardwareId()).isEqualTo("hardware-id");
        assertThat(dto.getDescription()).isEqualTo("A device description");
    }

    @Test
    public void testConvertToDTO_nullEntity() {
        // When
        DeviceDTO dto = DeviceMapper.convertToDTO(null);

        // Then
        assertThat(dto).isNull();
    }

    @Test
    void convertToEntity() {
        // Given
        DeviceDTO dto = DeviceDTO.builder()
                .sku("123")
                .hardwareId("hardware-id")
                .description("A device description")
                .build();

        // When
        DeviceEntity entity = DeviceMapper.convertToEntity(dto);

        // Then
        assertThat(entity).isNotNull();
        assertThat(entity.getSku()).isEqualTo("123");
        assertThat(entity.getHardwareId()).isEqualTo("hardware-id");
        assertThat(entity.getDescription()).isEqualTo("A device description");
    }

    @Test
    public void testConvertToEntity_nullDTO() {
        // When
        DeviceEntity entity = DeviceMapper.convertToEntity(null);

        // Then
        assertThat(entity).isNull();
    }

    @Test
    void convertToDTOList() {
        // Given
        List<DeviceEntity> entities = List.of(
                DeviceEntity.builder().sku("123").hardwareId("hardware-id-1").description("Description 1").build(),
                DeviceEntity.builder().sku("456").hardwareId("hardware-id-2").description("Description 2").build()
        );

        // When
        List<DeviceDTO> dtos = DeviceMapper.convertToDTOList(entities);

        // Then
        assertThat(dtos).isNotNull().hasSize(2);
        assertThat(dtos.get(0).getSku()).isEqualTo("123");
        assertThat(dtos.get(0).getHardwareId()).isEqualTo("hardware-id-1");
        assertThat(dtos.get(0).getDescription()).isEqualTo("Description 1");
        assertThat(dtos.get(1).getSku()).isEqualTo("456");
        assertThat(dtos.get(1).getHardwareId()).isEqualTo("hardware-id-2");
        assertThat(dtos.get(1).getDescription()).isEqualTo("Description 2");
    }

    @Test
    public void testConvertToDTOList_emptyList() {
        // Given
        List<DeviceEntity> entities = List.of();

        // When
        List<DeviceDTO> dtos = DeviceMapper.convertToDTOList(entities);

        // Then
        assertThat(dtos).isNotNull().isEmpty();
    }
}