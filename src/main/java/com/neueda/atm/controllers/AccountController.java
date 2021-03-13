package com.neueda.atm.controllers;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.neueda.atm.entities.Account;
import com.neueda.atm.service.AccountImpl;

/**
 * 
 * @author Joseph Keenan
 *
 */
@RestController
public class AccountController {

	@Autowired
	AccountImpl account;

	@PostConstruct
	private void setupData() {
		account.save(new Account(123456789, 1234, 800, 200));
		account.save(new Account(987654321, 4321, 1250, 150));
	}

	/**
	 * 
	 * @return
	 */
	@GetMapping("/accounts")
	public List<Account> getAccounts() {
		return account.findAll();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/accounts/{id}")
	public Account getAccountById(@PathVariable("id") Integer id) {
		System.out.println("fasgfaga " + id);
		return account.findById(id);
	}

}