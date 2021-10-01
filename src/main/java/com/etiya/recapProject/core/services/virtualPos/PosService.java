package com.etiya.recapProject.core.services.virtualPos;

public class PosService {
	
	public boolean checkCreditCard(double amount, String cardNumber) {
		if(checkLimit(amount) && checkCardNumber(cardNumber)) {
			return true;
		}
		return false;
	}
	
	private boolean checkLimit(double amount) {
		if (amount > 1000) {
			return false;
		}
		return true;
	}

	private boolean checkCardNumber(String cardNumber) {
		if (!cardNumber.equals("4111111111111111")) {
			return false;
		}
		return true;
	}
}
