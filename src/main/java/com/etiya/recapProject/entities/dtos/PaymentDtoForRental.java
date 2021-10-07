package com.etiya.recapProject.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDtoForRental {
	
	private String cardNumber;
	
	private String cvc;
	
	private String expiryDate;
	
	private boolean saveCreditCard;
}
