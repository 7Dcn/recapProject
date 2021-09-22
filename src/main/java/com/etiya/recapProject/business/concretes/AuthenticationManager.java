package com.etiya.recapProject.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.AuthenticationService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.business.BusinessRules;
import com.etiya.recapProject.core.utilities.results.ErrorResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.ApplicationUserDao;
import com.etiya.recapProject.entities.requests.loginRequest.LoginRequest;

@Service
public class AuthenticationManager implements AuthenticationService {

	ApplicationUserDao applicationUserDao;

	@Autowired
	public AuthenticationManager(ApplicationUserDao applicationUserDao) {
		super();
		this.applicationUserDao = applicationUserDao;
	}

	@Override
	public Result logIn(LoginRequest loginRequest) {
		var result = BusinessRules.run(checkEmailAndPassword(loginRequest));

		if (result != null) {
			return result;
		}

		return new SuccessResult(Messages.LOGINSUCCESS);
	}

	private Result checkEmailAndPassword(LoginRequest loginRequest) {
		if (!this.applicationUserDao.existsByEmail(loginRequest.getEmail())) {

			return new ErrorResult(Messages.LOGINEMAILERROR);
		}
		if (!this.applicationUserDao.getByEmail(loginRequest.getEmail()).getPassword().equals(loginRequest.getPassword())) {

			return new ErrorResult(Messages.LOGINPASSWORDERROR);
		}

		return new SuccessResult();
	}

}
