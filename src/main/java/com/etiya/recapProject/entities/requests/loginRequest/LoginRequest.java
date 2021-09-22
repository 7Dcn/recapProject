package com.etiya.recapProject.entities.requests.loginRequest;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
	
	@NotNull
	@NotBlank
	@Email(message = "email formatında giriniz")
	private String email;
	
	@NotNull
	@NotBlank
	private String password;
}
