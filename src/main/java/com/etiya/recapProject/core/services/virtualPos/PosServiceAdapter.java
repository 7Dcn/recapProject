package com.etiya.recapProject.core.services.virtualPos;

import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.PosCheckService;

@Service
public class PosServiceAdapter implements PosCheckService {

	@Override
	public boolean checkCreditCardInformation(double amount, String cardNumber, String cvc, String expiryDate) {
		PosService posService = new PosService();

		return posService.checkCreditCard(amount, cardNumber, cvc, expiryDate);

	}

}
