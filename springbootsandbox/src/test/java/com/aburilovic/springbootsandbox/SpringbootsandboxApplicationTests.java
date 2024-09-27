package com.aburilovic.springbootsandbox;

import com.aburilovic.springbootsandbox.dto.DeviceDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests that primarily tests Security (User authentication and authorization)
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.profiles.active=dev"
})
class SpringbootsandboxApplicationTests {

    private static final String BASE_URL = "/api/v1/devices/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "testUser", roles = {"User"})
    void testGetAllDevices() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].hardwareId").value("Samsung10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].hardwareId").value("Samsung12"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"User", "DeviceManager"})
    void testGetAllDevices_multipleRoles() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].hardwareId").value("Samsung10"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].hardwareId").value("Samsung12"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"Admin", "DeviceManager"})
    void testGetAllDevices_returnForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/devices/all"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"Admin", "DeviceManager"})
    void testGetAllDevicesByHardwareId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "hardwareId/Samsung12"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.hardwareId").value("Samsung12"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"User"})
    void testGetAllDevicesByHardwareId_returnForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "hardwareId/Samsung12"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"Admin"})
    public void testCreateDevice() throws Exception {
        DeviceDTO deviceDTO = DeviceDTO.builder()
                .sku("testsku1")
                .hardwareId("testharwareid1")
                .description("test description").build();

        mockMvc.perform(post(BASE_URL + "device")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deviceDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.hardwareId").value("testharwareid1"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"User", "DeviceManager"})
    public void testCreateDevice_returnForbidden() throws Exception {
        DeviceDTO deviceDTO = DeviceDTO.builder()
                .sku("testsku2")
                .hardwareId("testharwareid2")
                .description("test description 2").build();

        mockMvc.perform(post(BASE_URL + "device")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deviceDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"DeviceManager"})
    public void deleteDevice() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "device/sku/unknownSKU")
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"User", "Admin"})
    public void deleteDevice_returnForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "device/sku/SKU188")
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
