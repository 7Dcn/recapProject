package com.etiya.recapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.ApplicationUserService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.dataAccess.abstracts.ApplicationUserDao;
import com.etiya.recapProject.entities.abstracts.ApplicationUser;

@Service
public class ApplicationUserManager implements ApplicationUserService {

	@Autowired
	private ApplicationUserDao userDao;
	
	public ApplicationUserManager(ApplicationUserDao userDao) {
		super();
		this.userDao = userDao;
	}

	@Override
	public DataResult<List<ApplicationUser>> getAll() {
		return new SuccessDataResult<List<ApplicationUser>>(this.userDao.findAll(),Messages.USERLIST);
	}

}
