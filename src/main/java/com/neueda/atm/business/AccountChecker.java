package com.neueda.atm.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neueda.atm.entities.Account;
import com.neueda.atm.service.AccountImpl;

/**
 * 
 * @author Joseph Keenan
 *
 */
@Service
public class AccountChecker {

	@Autowired
	private AccountImpl accountImpl;

	/**
	 * 
	 * @param account
	 * @return
	 */
	public int getTotalBalanceForAccount(Account account) {
		return account.getOpeningBalance() + account.getOverdraft();
	}

	/**
	 * 
	 * @param account
	 * @param cashToWithdraw
	 * @return
	 */
	public boolean doesAccountHaveFunds(Account account, int cashToWithdraw) {
		return cashToWithdraw <= (account.getOpeningBalance() + account.getOverdraft());
	}

	/**
	 * 
	 */
	public void updateBalance(Account account, int cashToWithdraw) {
		int openingBalance = account.getOpeningBalance();

		if (openingBalance >= cashToWithdraw) {
			account.setOpeningBalance(account.getOpeningBalance() - cashToWithdraw);
		} else {
			account.setOpeningBalance(0);
			int amountOfOverdraftToRemove = cashToWithdraw - openingBalance;
			account.setOverdraft(account.getOverdraft() - amountOfOverdraftToRemove);
		}

		accountImpl.save(account);
	}

	public Account getAccont(int id) {
		return accountImpl.findById(id);
	}

}
