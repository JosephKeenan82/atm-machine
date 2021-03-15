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
public class AtmChecker {

	@Autowired
	private Atm atm;

	public void updateATMBalance(int cashToWithdraw) {
		atm.setCurrentBalance(atm.getCurrentBalance() - cashToWithdraw);

		// TODO: change notes

	}

	public boolean canDispenseThisExactAmount(int cashToWithdraw) {
		return cashToWithdraw % 5 == 0;
	}

}
