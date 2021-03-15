package com.neueda.atm.business;

import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Joseph Keenan
 *
 */
@Component
public class Atm {

	private Map<String, Integer> notesRemaining;
	private int currentBalance;

	public Atm() {
	}

	public Atm(Map<String, Integer> notesRemaining, int currentBalance) {
		this.notesRemaining = notesRemaining;
		this.currentBalance = currentBalance;
	}

	public Map<String, Integer> getNotesRemaining() {
		return notesRemaining;
	}

	public void setNotesRemaining(Map<String, Integer> notesRemaining) {
		this.notesRemaining = notesRemaining;
	}

	public int getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(int currentBalance) {
		this.currentBalance = currentBalance;
	}

}
