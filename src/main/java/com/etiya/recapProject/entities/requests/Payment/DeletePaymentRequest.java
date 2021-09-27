package com.etiya.recapProject.entities.requests.Payment;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeletePaymentRequest {
	
	@NotNull
	private int id;
}
