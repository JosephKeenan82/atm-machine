package com.neueda.atm.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class will be used to check if ATM has sufficient cash and determine
 * lowest number of notes to dispense
 * 
 * @author Joseph Keenan
 *
 */
@Service
public class ATMChecker {

	@Autowired
	private ATM atm;

	public void updateBalance(int cashToWithdraw) {
		atm.setCurrentBalance(atm.getCurrentBalance() - cashToWithdraw);

		// TODO: change notes

	}

	public boolean canDispenseThisExactAmount(int cashToWithdraw) {
		return cashToWithdraw % 5 == 0;
	}

}
