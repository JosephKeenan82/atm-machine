package com.neueda.atm.controllers;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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
	AccountImpl accountImpl;

	@PostConstruct
	private void setupData() {
		accountImpl.save(new Account(123456789, 1234, 800, 200));
		accountImpl.save(new Account(987654321, 4321, 1250, 150));
	}

	/**
	 * 
	 * @return
	 */
	@GetMapping("/accounts")
	public List<Account> getAccounts() {
		return accountImpl.findAll();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/accounts/{id}")
	public Account getAccountById(@PathVariable("id") Integer id) {
		return accountImpl.findById(id);
	}

	// - User (assume any rest client – curl, postman, browser) should be able to
	// request a balance check along with maximum withdrawal amount (if any),

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/balance")
	public String getBalance(@RequestParam("id") Integer id, @RequestParam("pin") Integer pin) {
		Account account = accountImpl.findById(id);

		if (account.getPin() == pin) {
			return "Account balance is " + account.getOpeningBalance() + " with overdraft of " + account.getOverdraft()
					+ ". Total withdrawal amount is " + (account.getOpeningBalance() + account.getOverdraft());
		} else {
			return "Incorrect pin, please try again";
		}
	}

}