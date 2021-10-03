package com.etiya.recapProject.business.abstracts;

public interface PosCheckService {
	boolean checkCreditCardInformation (double amount, String cardNumber, String cvc, String expiryDate);
}
