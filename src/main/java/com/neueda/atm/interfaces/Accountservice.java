package com.neueda.atm.interfaces;

import java.util.List;

import com.neueda.atm.entities.Account;

public interface Accountservice {

	List<Account> findAll();

	Account findById(int id);

	void save(Account account);

	void deleteById(int id);

}
