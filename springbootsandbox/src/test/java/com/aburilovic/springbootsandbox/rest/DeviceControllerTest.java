package com.aburilovic.springbootsandbox.rest;

import com.aburilovic.springbootsandbox.dto.DeviceDTO;
import com.aburilovic.springbootsandbox.entity.DeviceEntity;
import com.aburilovic.springbootsandbox.repository.DeviceRepository;
import com.aburilovic.springbootsandbox.service.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This test just partially loads spring context to tests Rest layer mostly focusing on input and output testing.
 * Security is mostly ignored, PreAuthorized method level annotation where role is verified is being ignored.
 * Security (Authentication and Authorization) is tested with the integration tests.
 */
@WebMvcTest(DeviceController.class)
@WithMockUser(username = "testUser")
class DeviceControllerTest {

    private static final String BASE_URL = "/api/v1/devices/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceService deviceService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @MockBean
    private DeviceRepository deviceRepository;

    List<DeviceDTO> devices;
    private DeviceDTO deviceDTO;
    private DeviceEntity deviceEntity;

    @BeforeEach
    void setUp() {
        devices = new ArrayList<>();
        devices.add(DeviceDTO.builder()
                .hardwareId("hardware1")
                .sku("sku1")
                .description("description 1")
                .build());
        devices.add(DeviceDTO.builder()
                .hardwareId("hardware2")
                .sku("sku2")
                .description("description 2")
                .build());

        deviceDTO = DeviceDTO.builder()
                .hardwareId("12345")
                .sku("sku12345")
                .build();

        deviceEntity = new DeviceEntity();
        deviceEntity.setHardwareId(deviceDTO.getHardwareId());
        deviceEntity.setSku(deviceDTO.getSku());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void hello() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "home"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from Spring Boot Device Controller!"));
    }

    @Test
    void messageKafka() throws Exception {
        String testMessage = "testMessageFromSpringBootApp";

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "msg/{val}", testMessage))
                .andExpect(status().isOk())
                .andExpect(content().string(testMessage));
    }

    @Test
    void getAllDevices() throws Exception {
        when(deviceService.getAllDevices()).thenReturn(devices);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].hardwareId").value("hardware1"))
                .andExpect(jsonPath("$[1].hardwareId").value("hardware2"));
    }

    @Test
    void getAllDevicesByHardwareId() throws Exception {
        when(deviceService.getDeviceByHardwareId(any())).thenReturn(DeviceDTO.builder().hardwareId("hardware1").build());

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "hardwareId/hardware1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.hardwareId").value("hardware1"));
    }

    @Test
    public void createDevice_ShouldReturn201_WhenDeviceIsCreated() throws Exception {
        // Mock the service to return the created device
        when(deviceService.createDevice(any(DeviceDTO.class))).thenReturn(deviceEntity);

        mockMvc.perform(post(BASE_URL + "device")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deviceDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.hardwareId").value("12345"));
    }

    @Test
    public void createDevice_ShouldReturn500_WhenDeviceCreationFails() throws Exception {
        // Mock the service to return null, simulating failure
        when(deviceService.createDevice(any(DeviceDTO.class))).thenReturn(null);

        mockMvc.perform(post(BASE_URL + "device")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deviceDTO)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void deleteDevice_Success() throws Exception {
        DeviceEntity deviceEntity = new DeviceEntity();
        deviceEntity.setId(1L);
        deviceEntity.setSku("12345");

        when(deviceService.getDeviceEntityBySku(anyString())).thenReturn(deviceEntity);
        when(deviceService.deleteDeviceById(1L)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "device/sku/12345")
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteDevice_NotFound() throws Exception {
        when(deviceService.getDeviceEntityBySku(anyString())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "device/sku/12345")
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteDevice_DeletionFailure() throws Exception {
        DeviceEntity deviceEntity = new DeviceEntity();
        deviceEntity.setId(1L);
        deviceEntity.setSku("12345");

        when(deviceService.getDeviceEntityBySku(anyString())).thenReturn(deviceEntity);
        doReturn(false).when(deviceService).deleteDeviceById(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "device/sku/12345")
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}