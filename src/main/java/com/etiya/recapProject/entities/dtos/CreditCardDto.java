package com.etiya.recapProject.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDto {
	
	private String cardNumber;

	private String cardName;

	private String cvc;

	private String expiryDate;

	private String customerEmail;
}
