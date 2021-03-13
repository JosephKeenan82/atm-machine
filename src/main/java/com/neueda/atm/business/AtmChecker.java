package com.neueda.atm.business;

import com.neueda.atm.entities.Account;

/**
 * This class will be used to check if ATM has sufficient cash and determine
 * lowest number of notes to dispense
 * 
 * @author Joseph Keenan
 *
 */
public class AtmChecker {

	/**
	 * 
	 * @param account
	 * @return
	 */
	public int getTotalBalance(Account account) {
		return account.getOpeningBalance() + account.getOverdraft();
	}

	/**
	 * 
	 * @param account
	 * @param cashToDispense
	 * @return
	 */
	public boolean doesAccountHaveFunds(Account account, int cashToDispense) {
		return cashToDispense <= (account.getOpeningBalance() + account.getOverdraft());
	}

}
