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
import org.testcontainers.junit.jupiter.Testcontainers;

//@formatter:off
@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.MethodName.class)
@TestPropertySource(locations="classpath:test.properties")
public class AtmControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Test
	public void givenEnoughCashInAccount_whenAccountRequestFunds_thenFundsShouldBeWithrawn() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.get("/cash")
				.param("id", "1")
				.param("pin", "1234")
				.param("cash", "735")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(
					equalTo("Withdrawing 735 from account 123456789 using {50=14, 20=1, 10=1, 5=1}")));
	}
	
	@Test
	public void givenEnoughCashInAccount_whenAccountRequestFunds_thenFundsShouldBeWithrawn_2() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.get("/cash")
				.param("id", "1")
				.param("pin", "1234")
				.param("cash", "20")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(
					equalTo("Withdrawing 20 from account 123456789 using {20=1}")));
	}
	
	@Test
	public void givenEnoughCashInAccount_whenAccountRequestFunds_thenFundsShouldBeWithrawn_3() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.get("/cash")
				.param("id", "1")
				.param("pin", "1234")
				.param("cash", "15")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(
					equalTo("Withdrawing 15 from account 123456789 using {10=1, 5=1}")));
	}
	
	@Test
	public void givenEnoughCashInAccount_whenAccountRequestFunds_thenFundsShouldBeWithrawn_4() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.get("/cash")
				.param("id", "1")
				.param("pin", "1234")
				.param("cash", "65")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(
					equalTo("Withdrawing 65 from account 123456789 using {50=1, 10=1, 5=1}")));
	}
	
	@Test
	public void givenNotEnoughCashInAccount_whenAccountRequestFunds_thenFundsShouldBeNotWithrawn() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.get("/cash")
				.param("id", "1")
				.param("pin", "1234")
				.param("cash", "500")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(
					equalTo("Not enough cash to withdraw 500, remaining balance is 165")));
	}
	
	@Test
	public void givenEnoughCashInAccount_whenPinIsincorrect_thenUserNotified() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.get("/cash")
				.param("id", "1")
				.param("pin", "123456")
				.param("cash", "600")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(
					equalTo("Incorrect pin, please try again")));
	}
	
	@Test
	public void givenATMHasCash_whenModuloFiveViolated_thenUserNotified() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.get("/cash")
				.param("id", "1")
				.param("pin", "1234")
				.param("cash", "72")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(
					equalTo("ATM cannot dispense this amount 72, please try again in multiple of fives")));
	}
	
	@Test
	public void givenATMHasCash_whenMoreCashIsRequestedThanATMHas_thenUserNotified() throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.get("/cash")
				.param("id", "1")
				.param("pin", "1234")
				.param("cash", "2000")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(
					equalTo("Not enough cash in atm to dispense 2000, atm currently has 1500")));
	}
	
	@Test
	public void balanceRequest_whenPinIsCorrect_thenReturnBalance() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/balance")
				.param("id", "1")
				.param("pin", "1234")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo(
				"Account balance is 800 with overdraft of 200. Total withdrawal amount is 1000")));
	}
	
	@Test
	public void balanceRequest_whenPinIsInCorrect_thenDoNotReturnBalance() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/balance")
				.param("id", "1")
				.param("pin", "123456")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("Incorrect pin, please try again")));
	}
	
}
