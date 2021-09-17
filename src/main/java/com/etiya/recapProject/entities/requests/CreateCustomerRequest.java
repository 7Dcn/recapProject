package com.etiya.recapProject.entities.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerRequest {
	
	private String companyName;
	
	private String email;
	
	private String password;
}
