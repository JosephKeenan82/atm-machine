package com.neueda.atm.business;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neueda.atm.entities.Atm;

/**
 * This class will be used to check if ATM has sufficient cash and determine
 * lowest number of notes to dispense
 * 
 * @author Joseph Keenan
 *
 */
@Service
public class AtmChecker {

	@Autowired
	private Atm atm;

	private int value = 0;

	public boolean canDispenseThisExactAmount(int cashToWithdraw) {
		return cashToWithdraw % 5 == 0;
	}

	public Map<Integer, Integer> updateATMBalanceAndGetNotesToDispense(int cashToWithdraw) {
		Map<Integer, Integer> notesToDispense = new LinkedHashMap<>();
		Map<Integer, Integer> notesInAtm = atm.getNotesInAtm();

		notesInAtm.forEach((k, v) -> {

			for (int i = 0; i < v; i++) {
				// If the amount to withdraw is less than the current value then move to next
				if (cashToWithdraw < k) {
					break;
				}

				if (value < cashToWithdraw) {
					value += k;// add this amount to the current value to be withdrawn
					updateMaps(notesToDispense, notesInAtm, k);
				}

				// This is a tidy up block
				if (value > cashToWithdraw) {
					tidyUp(notesToDispense, notesInAtm, k);
				}
			}
		});

		// update atm
		atm.setNotesInAtm(notesInAtm);
		atm.setCurrentBalance(atm.getCurrentBalance() - cashToWithdraw);

		return notesToDispense;

	}

	private void updateMaps(Map<Integer, Integer> notesToDispense, Map<Integer, Integer> notesInAtm, Integer k) {
		// Create mapping for notes withdrawn and update note in atm
		if (!notesToDispense.containsKey(k)) {
			notesToDispense.put(k, 1);
			notesInAtm.put(k, notesInAtm.get(k) - 1);
		} else {
			notesToDispense.put(k, notesToDispense.get(k) + 1);
			notesInAtm.put(k, notesInAtm.get(k) - 1);
		}
	}

	private void tidyUp(Map<Integer, Integer> notesToDispense, Map<Integer, Integer> notesInAtm, Integer k) {
		value -= k;
		notesToDispense.put(k, notesToDispense.get(k) - 1);

		if (notesToDispense.get(k) == 0) {
			notesToDispense.remove(k);
		}

		notesInAtm.put(k, notesInAtm.get(k) + 1);
	}
}
