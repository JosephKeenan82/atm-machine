package com.neueda.atm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neueda.atm.dao.AccountRepository;
import com.neueda.atm.entities.Account;
import com.neueda.atm.exception.IDNotFoundException;
import com.neueda.atm.interfaces.Accountservice;

// The methods do not need to be @Transactional as we get this out of the box
// Delegating methods to Spring Data JPA

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
	public Account findById(int id) throws IDNotFoundException {
		Optional<Account> result = accountRepository.findById(id);

		Account theAccount = null;
		if (result.isPresent()) {
			theAccount = result.get();
		} else {
			throw new IDNotFoundException("Account " + id + " not found!");
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

}
