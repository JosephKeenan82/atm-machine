package com.neueda.atm.interfaces;

import java.util.List;

import com.neueda.atm.entities.Account;
import com.neueda.atm.exception.IDNotFoundException;

/**
 * 
 * @author Joseph Keenan
 *
 */
public interface Accountservice {

	List<Account> findAll();

	Account findById(int id) throws IDNotFoundException;

	void save(Account account);

	void deleteById(int id);

}
