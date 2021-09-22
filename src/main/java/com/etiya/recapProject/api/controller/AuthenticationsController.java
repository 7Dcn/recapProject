package com.etiya.recapProject.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.recapProject.business.abstracts.AuthenticationService;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.requests.loginRequest.LoginRequest;

@RestController
@RequestMapping("api/authentication")
public class AuthenticationsController {
	AuthenticationService authenticationService;

	@Autowired
	public AuthenticationsController(AuthenticationService authenticationService) {
		super();
		this.authenticationService = authenticationService;
	}

	@PostMapping("/logIn")
	public Result logIn(LoginRequest loginRequest) {
		return this.authenticationService.logIn(loginRequest);
	}

}
