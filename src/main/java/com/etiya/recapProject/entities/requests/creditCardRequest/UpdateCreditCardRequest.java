package com.etiya.recapProject.entities.requests.creditCardRequest;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCreditCardRequest {

	@NotNull
	private int cardId;

	@NotNull
	private int customerId;
	
	@NotNull
	private String cardNumber;

	@NotNull
	private String cardName;

	@NotNull
	private String cvc;

	@NotNull
	private String expiryDate;
}
