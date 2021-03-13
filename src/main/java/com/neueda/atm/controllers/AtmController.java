package com.neueda.atm.controllers;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neueda.atm.business.AccountChecker;
import com.neueda.atm.business.AtmChecker;
import com.neueda.atm.entities.Account;
import com.neueda.atm.entities.Atm;
import com.neueda.atm.service.AccountImpl;

/**
 * 
 * @author JKE
 *
 */
@RestController
public class AtmController {

	@Autowired
	private AccountImpl accountImpl;

	@Autowired
	private Atm atm;

	private AccountChecker accChecker = new AccountChecker();

	private AtmChecker atmChecker = new AtmChecker();

	@PostConstruct
	private void setupData() {
		atm.setCurrentBalance(1500);
		Map<String, Integer> notes = Stream
				.of(new Object[][] { { "Fifty", 10 }, { "Twenty", 30 }, { "Ten", 30 }, { "Five", 20 }, })
				.collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));
		atm.setNotesRemaining(notes);
	}

	/**
	 * 
	 * @param id
	 * @param pin
	 * @param cashToDispense
	 * @return
	 */
	@GetMapping("/cash")
	public String getCash(@RequestParam("id") int id, @RequestParam("pin") int pin,
			@RequestParam("cash") int cashToDispense) {

		Account account = accountImpl.findById(id);

		if (account.getPin() == pin && accChecker.getTotalBalanceForAccount(account) >= cashToDispense
				&& atm.getCurrentBalance() >= cashToDispense) {
			if (account.getOpeningBalance() >= cashToDispense) {
				account.setOpeningBalance(account.getOpeningBalance() - cashToDispense);
				accountImpl.save(account);
				return "Dispensing " + cashToDispense + " from deposit " + account.getAccountNumber();
			} else if (account.getOverdraft() >= cashToDispense) {
				account.setOverdraft(account.getOverdraft() - cashToDispense);
				accountImpl.save(account);
				return "Dispensing " + cashToDispense + " from overdraft " + account.getAccountNumber();
			}
		}
		return "Not enough cash to withdraw " + cashToDispense + ", remainingbalance is "
				+ accChecker.getTotalBalanceForAccount(account);
	}

	@GetMapping("/atmcash")
	public int getCashInAtm() {
		return atm.getCurrentBalance();
	}

}
