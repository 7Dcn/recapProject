package com.etiya.recapProject.entities.requests.corporateCustomerRequest;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCorporateCustomerRequest {
	
	@NotNull
	private int id;
}
