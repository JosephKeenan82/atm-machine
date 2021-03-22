package com.demo.atm.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.atm.entities.Account;
import com.demo.atm.service.AccountImpl;

/**
 * This class will be used to do checks on the {@link Account} class
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
	 * This method will be used to get the total balance of associated
	 * {@link Account}
	 * 
	 * @param account the account to check
	 * @return Balance combination of openingBalance and overdraft
	 */
	public int getTotalBalanceForAccount(Account account) {
		logger.info("Total balance for account {} is {}", account.getId(),
				(account.getOpeningBalance() + account.getOverdraft()));
		return account.getOpeningBalance() + account.getOverdraft();
	}

	/**
	 * This method will be used to update balance of associated {@link Account}
	 * 
	 * @param account        the account to update
	 * @param cashToWithdraw cash to be withdrawn from account
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

	/**
	 * Retrieve an {@link Account}
	 * 
	 * @param id the id of the account
	 * @return the account
	 */
	public Account getAccont(int id) {
		logger.info("Retreiving account with id {}", id);
		return accountImpl.findById(id);
	}

}
