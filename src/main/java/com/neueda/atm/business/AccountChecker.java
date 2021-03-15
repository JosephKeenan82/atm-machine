package com.neueda.atm.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	Logger logger = LoggerFactory.getLogger(AccountChecker.class);

	/**
	 * 
	 * @param account
	 * @return
	 */
	public int getTotalBalanceForAccount(Account account) {
		logger.info("Total balance for account {} is {}", account.getId(),
				(account.getOpeningBalance() + account.getOverdraft()));
		return account.getOpeningBalance() + account.getOverdraft();
	}

	/**
	 * 
	 */
	public void updateAccountBalance(Account account, int cashToWithdraw) {
		logger.info("Updating account balance for {}, withdrawing {}", account.getId(), cashToWithdraw);
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
		logger.info("Retreiving account with id {}", id);
		return accountImpl.findById(id);
	}

}
