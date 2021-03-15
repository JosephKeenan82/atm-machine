package com.neueda.atm.controllers;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neueda.atm.entities.Account;
import com.neueda.atm.service.AccountImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * This is the controller class for {@link Account}
 * 
 * @author Joseph Keenan
 *
 */
@RestController
public class AccountController {

	@Autowired
	AccountImpl accountImpl;

	private Logger logger = LoggerFactory.getLogger(AccountController.class);

	@PostConstruct
	private void setupAccountData() {
		logger.info("Initializing accounts");
		accountImpl.save(new Account(123456789, 1234, 800, 200));
		accountImpl.save(new Account(987654321, 4321, 1250, 150));
	}

	/**
	 * This REST endpoint is used to get all {@link Account}s
	 * 
	 * @return list of accounts
	 */
	@ApiOperation(value = "Get accounts", notes = "This method returns all accounts")
	@GetMapping("/account")
	public List<Account> getAccounts() {
		logger.info("Retreiving accounts");
		return accountImpl.findAll();
	}

	/**
	 * This REST endpoint is used to get an {@link Account} based on an id
	 * 
	 * @param id the id of the account
	 * @return
	 * @throws IDNotFoundException
	 */
	@ApiOperation(value = "Get an account", notes = "This method returns an account based on id")
	@GetMapping("/account/{id}")
	public Account getAccountById(
			@ApiParam(name = "id", type = "int", value = "Account id", required = true) @PathVariable("id") Integer id) {
		logger.info("Retreiving account with id {}", id);
		return accountImpl.findById(id);
	}

	/**
	 * This REST endpoint is used to retrieve balance of an {@link Account} based on
	 * an id
	 * 
	 * @param id the id of the account
	 * @return balance of the account
	 */
	@ApiOperation(value = "Get balance for an account", notes = "This method returns balance for account")
	@GetMapping("/balance")
	public String getBalance(
			@ApiParam(name = "id", type = "int", value = "Account id", required = true) @RequestParam("id") Integer id,
			@ApiParam(name = "pin", type = "int", value = "Account pin", required = true) @RequestParam("pin") Integer pin) {
		Account account = accountImpl.findById(id);
		logger.info("Retreiving balance for account with id {}", id);
		if (account.getPin() == pin) {
			return "Account balance is " + account.getOpeningBalance() + " with overdraft of " + account.getOverdraft()
					+ ". Total withdrawal amount is " + (account.getOpeningBalance() + account.getOverdraft());
		} else {
			return "Incorrect pin, please try again";
		}
	}

	/**
	 * This REST endpoint is used to create an {@link Account}
	 * 
	 * @param accountNumber  the account number of the account
	 * @param pin            the pin of the account
	 * @param openingBalance the openingBalance of the account
	 * @param overdraft      the overdraft of the account
	 * @return new id of the account
	 */
	@ApiOperation(value = "Create an account", notes = "This method creates an account")
	@PostMapping("/account")
	public String createAccount(
			@ApiParam(name = "accountNumber", type = "int", value = "account number", required = true) @RequestParam("accountNumber") Integer accountNumber,
			@ApiParam(name = "pin", type = "int", value = "Account pin", required = true) @RequestParam("pin") Integer pin,
			@ApiParam(name = "openingbalance", type = "int", value = "Account opening balance", required = true) @RequestParam("openingBalance") Integer openingBalance,
			@ApiParam(name = "overdraft", type = "int", value = "Account overdraft", required = true) @RequestParam("overdraft") Integer overdraft) {
		Account account = new Account(accountNumber, pin, openingBalance, overdraft);
		logger.info("Creating account: accountNumber {}, pin {}, openingBalance {}, overdraft {}", accountNumber, pin,
				openingBalance, overdraft);
		accountImpl.save(account);
		return "New account id: " + account.getId();
	}

	/**
	 * This REST endpoint is used to update an {@link Account} pin
	 * 
	 * @param id     the id of the account
	 * @param oldPin the old (current) pin of the account
	 * @param newPin the new pin of the account
	 * @return success message or message statin oldPin is incorrect
	 */
	@ApiOperation(value = "Update pin", notes = "Updates pin for an account")
	@GetMapping("/updatepin")
	public String updatePin(
			@ApiParam(name = "id", type = "int", value = "Account id", required = true) @RequestParam("id") Integer id,
			@ApiParam(name = "oldpin", type = "int", value = "Accounts current pin", required = true) @RequestParam("oldpin") Integer oldPin,
			@ApiParam(name = "newpin", type = "int", value = "Account new pin", required = true) @RequestParam("newpin") Integer newPin) {
		Account account = accountImpl.findById(id);
		logger.info("Updating pin for account {}", id);
		if (oldPin == account.getPin()) {
			account.setPin(newPin);
			accountImpl.save(account);
			return "Updated pin successfully";
		}

		return "Incorrect old pin, please try again";
	}

	/**
	 * This REST endpoint is used to delete an {@link Account}
	 * 
	 * @param id the id of the account
	 * @return message informing id account was deleted
	 */
	@ApiOperation(value = "Delete an account", notes = "This method deletes an account")
	@DeleteMapping("/account")
	public String deleteAccount(
			@ApiParam(name = "id", type = "int", value = "Account id", required = true) @RequestParam("id") Integer id) {
		logger.info("Deleting account {}", id);
		accountImpl.deleteById(id);
		return "Deleted account id: " + id;
	}

	/**
	 * This REST endpoint is used get a count of {@link Account}s
	 * 
	 * @return number of accounts
	 */
	@ApiOperation(value = "Count account", notes = "This method gets count of all accounts")
	@GetMapping("/countaccounts")
	public String getCountOfAccounts() {
		return "Number of accounts: " + accountImpl.countAccounts();
	}

}