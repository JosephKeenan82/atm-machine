package com.neueda.atm.business;

import org.springframework.beans.factory.annotation.Autowired;

import com.neueda.atm.entities.Account;
import com.neueda.atm.service.AccountImpl;

public class AccountLogic {

	@Autowired
	private AccountImpl accountImpl;

	public int getTotalBalance(Account account) {
		return account.getOpeningBalance() + account.getOverdraft();
	}

	public boolean doesAccountHaveFunds(Account account, int cashToDispense) {
		return cashToDispense <= (account.getOpeningBalance() + account.getOverdraft());
	}

}
