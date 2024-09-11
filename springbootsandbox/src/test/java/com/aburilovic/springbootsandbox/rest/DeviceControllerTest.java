package com.aburilovic.springbootsandbox.rest;

import com.aburilovic.springbootsandbox.dto.DeviceDTO;
import com.aburilovic.springbootsandbox.repository.DeviceRepository;
import com.aburilovic.springbootsandbox.service.DeviceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeviceController.class)
class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceService deviceService;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @MockBean
    private DeviceRepository deviceRepository;

    List<DeviceDTO> devices;

    @BeforeEach
    void setUp() {
        devices = new ArrayList<>();
        devices.add(DeviceDTO.builder()
                .hardwareId("harware1")
                .sku("sku1")
                .description("description 1")
                .build());
        devices.add(DeviceDTO.builder()
                .hardwareId("harware2")
                .sku("sku2")
                .description("description 2")
                .build());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void hello() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/devices/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from Spring Boot Device Controller!"));
    }

    @Test
    void messageKafka() throws Exception {
        String testMessage = "testMessageFromSpringBootApp";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/devices/msg/{val}", testMessage))
                .andExpect(status().isOk())
                .andExpect(content().string(testMessage));
    }

    @Test
    void getAllDevices() throws Exception {
        Mockito.when(deviceService.getAllDevices()).thenReturn(devices);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/devices/all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].hardwareId").value("harware1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].hardwareId").value("harware2"));
    }
}