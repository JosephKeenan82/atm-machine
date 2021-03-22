package com.demo.atm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.atm.dao.AccountRepository;
import com.demo.atm.entities.Account;
import com.demo.atm.interfaces.Accountservice;

/**
 * 
 * @author Joseph Keenan
 *
 */
@Service
public class AccountImpl implements Accountservice {

	private AccountRepository accountRepository;

	@Autowired
	public AccountImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

	@Override
	public Account findById(int id) {
		Optional<Account> result = accountRepository.findById(id);
		Account theAccount = null;
		if (result.isPresent()) {
			theAccount = result.get();
		}

		return theAccount;
	}

	@Override
	public void save(Account account) {
		accountRepository.save(account);
	}

	@Override
	public void deleteById(int id) {
		accountRepository.deleteById(id);
	}

	public long countAccounts() {
		return accountRepository.count();
	}

}
