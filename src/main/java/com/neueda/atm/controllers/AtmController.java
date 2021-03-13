package com.neueda.atm.controllers;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neueda.atm.entities.Account;
import com.neueda.atm.entities.Atm;
import com.neueda.atm.service.AccountImpl;

@RestController
public class AtmController {

	@Autowired
	private AccountImpl accountImpl;

	@Autowired
	private Atm atm;

	@PostConstruct
	private void setupData() {
		atm.setCurrentBalance(1500);

		Map<String, Integer> notes = Stream
				.of(new Object[][] { { "Fifty", 10 }, { "Twenty", 30 }, { "Ten", 30 }, { "Five", 20 }, })
				.collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));

		atm.setNotesRemaining(notes);
	}

	@GetMapping("/cash")
	public String getCash(@RequestParam("id") int id, @RequestParam("pin") int pin,
			@RequestParam("cash") int cashToDispense) {

		Account account = accountImpl.findById(id);
		if (account.getPin() == pin && cashToDispense <= (account.getOpeningBalance() + account.getOverdraft())) {
			return "Dispensing " + cashToDispense + " from account " + account.getAccountNumber();
		}

		return "not implemented!";
	}

}
