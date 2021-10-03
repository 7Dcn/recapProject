package com.etiya.recapProject.entities.requests.paymentRequest;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
	
	@NotNull
	private String cardNumber;
	
	@NotNull
	private String cvc;
	
	@NotNull
	private String expiryDate;
	
	@NotNull
	private double amount;
	
}
