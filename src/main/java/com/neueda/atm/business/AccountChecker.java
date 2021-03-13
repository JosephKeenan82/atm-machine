package com.neueda.atm.business;

import com.neueda.atm.entities.Account;

/**
 * 
 * @author Joseph Keenan
 *
 */
public class AccountChecker {

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
