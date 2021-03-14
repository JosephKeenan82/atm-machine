package com.neueda.atm.controllers;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neueda.atm.entities.Account;
import com.neueda.atm.service.AccountImpl;

/**
 * TODO: Add create & delete for account and test it
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
	@GetMapping("/account")
	public List<Account> getAccounts() {
		return accountImpl.findAll();
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws IDNotFoundException
	 */
	@GetMapping("/account/{id}")
	public Account getAccountById(@PathVariable("id") Integer id) {
		return accountImpl.findById(id);
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws IDNotFoundException
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

	/**
	 * 
	 * @param accountNumber
	 * @param pin
	 * @param openingBalance
	 * @param overdraft
	 * @return
	 */
	@PostMapping("/account")
	public String createAccount(@RequestParam("accountNumber") Integer accountNumber, @RequestParam("pin") Integer pin,
			@RequestParam("openingBalance") Integer openingBalance, @RequestParam("overdraft") Integer overdraft) {
		Account account = new Account(accountNumber, pin, openingBalance, overdraft);
		accountImpl.save(account);
		return "New account id: " + account.getId();
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws IDNotFoundException
	 */
	@GetMapping("/updatepin")
	public String updatePin(@RequestParam("id") Integer id, @RequestParam("oldpin") Integer oldPin,
			@RequestParam("newpin") Integer newPin) {
		Account account = accountImpl.findById(id);

		if (oldPin == account.getPin()) {
			account.setPin(newPin);
			accountImpl.save(account);
			return "Updated pin successfully";
		}

		return "Incorrect old pin, please try again";
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/account")
	public String createAccount(@RequestParam("id") Integer id) {
		accountImpl.deleteById(id);
		return "Deleted account id: " + id;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/countaccounts")
	public String getCountOfAccounts() {
		return "Number of accounts: " + accountImpl.countAccounts();
	}

}