package com.neueda.atm.entities;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class Atm {

//	-	should initialize with €1500 made up of 10 x €50s, 30 x €20s, 30 x €10s and 20 x €5s
//	-	should not dispense funds if the pin is incorrect,
//	-	cannot dispense more money than it holds,
//	-	cannot dispense more funds than customer have access to
//	-	should not expose the customer balance if the pin is incorrect,
//	-	should only dispense the exact amounts requested,
//	-	should dispense the minimum number of notes per withdrawal,

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
