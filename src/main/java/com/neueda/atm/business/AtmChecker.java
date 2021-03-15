package com.neueda.atm.business;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neueda.atm.entities.Atm;

/**
 * This class will be used to check if {@link Atm} has sufficient cash and
 * determine lowest number of notes to dispense
 * 
 * @author Joseph Keenan
 */
@Service
public class AtmChecker {

	@Autowired
	private Atm atm;

	private Logger logger = LoggerFactory.getLogger(AtmChecker.class);

	/**
	 * This method will check if the {@link Atm} can diwpense this amount
	 * 
	 * @param cashToWithdraw the amount to withdraw
	 * @return true or false
	 */
	public boolean canDispenseThisExactAmount(int cashToWithdraw) {
		logger.info("Checking if atm can dispense amount {}", cashToWithdraw);
		return cashToWithdraw % 5 == 0;
	}

	/**
	 * This method will update {@link Atm} balance and return minimum number of
	 * notes to satisfy the withdrawal
	 * 
	 * @param cashToWithdraw the amount to withdraw
	 * @return map of number of notes and denominations
	 */
	public Map<Integer, Integer> updateATMBalanceAndGetNotesToDispense(int cashToWithdraw) {
		Map<Integer, Integer> notesToDispense = new LinkedHashMap<>();
		Map<Integer, Integer> notesInAtm = atm.getNotesInAtm();
		int count = 0;

		Set<Integer> keySet = notesInAtm.keySet();
		Object[] array = keySet.toArray();

		for (int i = 0; i < notesInAtm.size(); i++) {
			count = cashToWithdraw / (Integer) array[i];
			cashToWithdraw = cashToWithdraw % (Integer) array[i]; // finding the remaining amount

			if (count != 0) {
				notesToDispense.put((Integer) array[i], count);
				notesInAtm.put((Integer) array[i], count);
			}
		}

		// update atm
		logger.info("Updating note in atm to {}", notesInAtm);
		atm.setNotesInAtm(notesInAtm);
		logger.info("Setting atm balance to {}", (atm.getCurrentBalance() - cashToWithdraw));
		atm.setCurrentBalance(atm.getCurrentBalance() - cashToWithdraw);

		logger.info("notesToDispense {}", notesToDispense);
		return notesToDispense;

	}

}