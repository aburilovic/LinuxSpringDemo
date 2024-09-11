package com.aburilovic.springbootsandbox;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SpringbootsandboxApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testGetAllDevices() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/devices/all"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].hardwareId").value("Samsung10"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].hardwareId").value("Samsung12"));
	}

}
