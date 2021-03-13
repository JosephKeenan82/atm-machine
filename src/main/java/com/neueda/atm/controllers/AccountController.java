package com.neueda.atm.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neueda.atm.entities.Account;

@RestController
public class AccountController {

	private List<Account> users;

	@PostConstruct
	public void loadData() {
		users = new ArrayList<>();
		users.add(new Account("123456789", 1234, 800.00, 200));
		users.add(new Account("987654321", 4321, 1250.00, 150));
	}

	@GetMapping("/users")
	public List<Account> getUsers() {
		return users;
	}

}