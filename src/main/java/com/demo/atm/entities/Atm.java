package com.demo.atm.entities;

import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * This class is used to set data for ATM
 * 
 * @author Joseph Keenan
 *
 */
@Component
public class Atm {

	private Map<Integer, Integer> notesInAtm;
	private int currentBalance;

	public Atm() {
		// not used
	}

	public Map<Integer, Integer> getNotesInAtm() {
		return notesInAtm;
	}

	public void setNotesInAtm(Map<Integer, Integer> notesInAtm) {
		this.notesInAtm = notesInAtm;
	}

	public int getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(int currentBalance) {
		this.currentBalance = currentBalance;
	}

}
