package com.neueda.atm.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

//@formatter:off
@SpringBootTest
@AutoConfigureMockMvc
public class AtmControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void getCashFromAccount() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/cash")
				.param("id", "1")
				.param("pin", "1234")
				.param("cash", "500")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(
					equalTo("Dispensing 500 from deposit 123456789")));
		
		
		
	}
}
