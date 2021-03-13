package com.neueda.atm.controllers;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AtmController {

	@PostConstruct
	public void loadData() {
// initialise cash
	}

	@GetMapping("/dispensecash")
	public String getCash() {
		return "not implemented!";
	}

}
