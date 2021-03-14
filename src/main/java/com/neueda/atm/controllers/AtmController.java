package com.neueda.atm.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neueda.atm.business.ATM;
import com.neueda.atm.business.ATMChecker;
import com.neueda.atm.business.AccountChecker;
import com.neueda.atm.entities.Account;

/**
 * 
 * @author Joseph Keenan
 *
 */
@RestController
public class ATMController {

	@Autowired
	private ATM atm;

	@Autowired
	private AccountChecker accChecker;

	@Autowired
	private ATMChecker atmChecker;

	@PostConstruct
	private void setupData() {
		atm.setCurrentBalance(1500);

		Map<String, Integer> notes = new HashMap<>();
		notes.put("Fifty", 10);
		notes.put("Twenty", 30);
		notes.put("Ten", 30);
		notes.put("Five", 20);

		atm.setNotesRemaining(notes);
	}

	/**
	 * 
	 * @param id
	 * @param pin
	 * @param cashToDispense
	 * @return
	 * @throws IDNotFoundException
	 */
	@GetMapping("/cash")
	public String getCash(@RequestParam("id") int id, @RequestParam("pin") int pin,
			@RequestParam("cash") int cashToWithdraw) {
		Account account = accChecker.getAccont(id);

		if (account.getPin() != pin) {
			return "Incorrect pin, please try again";
		}

		if (atm.getCurrentBalance() < cashToWithdraw) {
			return "Not enough cash in atm to dispense " + cashToWithdraw + ", atm currently has "
					+ atm.getCurrentBalance();
		}

		if (!atmChecker.canDispenseThisExactAmount(cashToWithdraw)) {
			return "ATM cannot dispense this amount " + cashToWithdraw + ", please try again";
		}

		if (accChecker.getTotalBalanceForAccount(account) >= cashToWithdraw) {
			accChecker.updateAccountBalance(account, cashToWithdraw);
			atmChecker.updateATMBalance(cashToWithdraw);
			// TODO: add details of notes
			return "Withdrawing " + cashToWithdraw + " from account " + account.getAccountNumber();
		}

		return "Not enough cash to withdraw " + cashToWithdraw + ", remaining balance is "
				+ accChecker.getTotalBalanceForAccount(account);
	}

	@GetMapping("/atmcash")
	public String getCashInAtm() {
		return "ATM Balance is " + atm.getCurrentBalance();
	}

}
