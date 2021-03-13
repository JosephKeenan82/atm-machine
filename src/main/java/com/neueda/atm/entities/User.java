package com.neueda.atm.entities;

public class User {

	private String accountNumber;
	private int pin;
	private double openingBalance;
	private int overdraft;

	public User(String accountNumber, int pin, double openingBalance, int overdraft) {
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.openingBalance = openingBalance;
		this.overdraft = overdraft;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public double getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}

	public int getOverdraft() {
		return overdraft;
	}

	public void setOverdraft(int overdraft) {
		this.overdraft = overdraft;
	}

}
