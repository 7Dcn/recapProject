package com.etiya.recapProject.business.abstracts;

import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.requests.loginRequest.LoginRequest;

public interface AuthenticationService {
	Result logIn(LoginRequest loginRequest);
}
