package com.neueda.atm.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

//@formatter:off
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.MethodName.class)
@TestPropertySource(locations="classpath:test.properties")
public class AccountControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void getAccounts() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/account")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void getAccount() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/account/2")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo(
				"{\"id\":2,\"accountNumber\":987654321,\"pin\":4321,\"openingBalance\":1250,\"overdraft\":150}")));
	}
	
	// save and then delete
	@Test
	public void createAccountUpdatePinAndDeleteAcount() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/account")
				.param("accountNumber", "1122334455")
				.param("pin", "888")
				.param("openingBalance", "1000")
				.param("overdraft", "500")
			.accept(MediaType.APPLICATION_JSON))			
		.andExpect(status().isOk())
		.andExpect(content().string(equalTo("New account id: 3")));
		
		mvc.perform(MockMvcRequestBuilders.get("/updatepin")
				.param("id", "3")
				.param("oldpin", "888")
				.param("newpin", "999")
				.accept(MediaType.APPLICATION_JSON))			
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("Updated pin successfully")));
		
		mvc.perform(MockMvcRequestBuilders.delete("/account")
				.param("id", "3")
				.accept(MediaType.APPLICATION_JSON))			
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("Deleted account id: 3")));
	} 

	@Test
	public void countAccounts() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/countaccounts")
			.accept(MediaType.APPLICATION_JSON))			
		.andExpect(status().isOk())
		.andExpect(content().string(equalTo("Number of accounts: 2")));
	}
	
}
