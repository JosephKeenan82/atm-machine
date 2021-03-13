package com.neueda.atm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neueda.atm.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}
