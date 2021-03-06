package com.demo.atm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.atm.entities.Account;

/**
 * This uses Spring Data JPA to automatically create CRUD operations for
 * {@link Account}
 * 
 * @author Joseph Keenan
 *
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {

}
