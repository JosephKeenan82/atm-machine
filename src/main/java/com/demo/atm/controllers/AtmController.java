package com.demo.atm.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.atm.business.AccountChecker;
import com.demo.atm.business.AtmChecker;
import com.demo.atm.entities.Account;
import com.demo.atm.entities.Atm;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * This class will be used to perform checks on the {@link Atm} class
 * 
 * @author Joseph Keenan
 *
 */
@RestController
public class AtmController {

	@Autowired
	private Atm atm;

	@Autowired
	private AccountChecker accChecker;

	@Autowired
	private AtmChecker atmChecker;

	private Logger logger = LoggerFactory.getLogger(AtmController.class);

	@PostConstruct
	private void setupAtmData() {
		logger.info("Initializing atm");
		atm.setCurrentBalance(1500);

		Map<Integer, Integer> notes = new LinkedHashMap<>();// maintain order
		notes.put(50, 20);
		notes.put(20, 30);
		notes.put(10, 30);
		notes.put(5, 20);

		atm.setNotesInAtm(notes);
	}

	/**
	 * This method will be used to withdraw cash from the {@link Atm}
	 * 
	 * @param id             the id of the acocunt
	 * @param pin            the pin of the account
	 * @param cashToWithdraw the cash to withdraw
	 * @return notification as to success of operation
	 */
	@ApiOperation(value = "Get cash", notes = "This method withdraws cash for an acocunt")
	@GetMapping("/cash")
	public String getCash(
			@ApiParam(name = "id", type = "int", value = "Account id", required = true) @RequestParam("id") Integer id,
			@ApiParam(name = "pin", type = "int", value = "Accounts current pin", required = true) @RequestParam("pin") Integer pin,
			@ApiParam(name = "cash", type = "int", value = "Cash to withdraw", required = true) @RequestParam("cash") Integer cashToWithdraw) {
		Account account = accChecker.getAccont(id);

		if (account.getPin() != pin) {
			logger.info("Pin is incorrect {}", pin);
			return "Incorrect pin, please try again";
		}

		if (atm.getCurrentBalance() < cashToWithdraw) {
			logger.info("ATM does not have enough cash. Current balance is {}", atm.getCurrentBalance());
			return "Not enough cash in atm to dispense " + cashToWithdraw + ", atm currently has "
					+ atm.getCurrentBalance();
		}

		if (!atmChecker.canDispenseThisExactAmount(cashToWithdraw)) {
			logger.info("ATM cannot dispense this amount {}", cashToWithdraw);
			return "ATM cannot dispense this amount " + cashToWithdraw + ", please try again in multiple of fives";
		}

		if (accChecker.getTotalBalanceForAccount(account) >= cashToWithdraw) {
			accChecker.updateAccountBalance(account, cashToWithdraw);
			Map<Integer, Integer> notesDispensed = atmChecker.updateATMBalanceAndGetNotesToDispense(cashToWithdraw);
			return "Withdrawing " + cashToWithdraw + " from account " + account.getAccountNumber() + " using "
					+ notesDispensed + ", remaining balance is " + accChecker.getTotalBalanceForAccount(account);
		}

		return "Not enough cash to withdraw " + cashToWithdraw + ", remaining balance is "
				+ accChecker.getTotalBalanceForAccount(account);
	}
}
