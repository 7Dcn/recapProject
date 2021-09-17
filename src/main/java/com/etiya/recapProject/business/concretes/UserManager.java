package com.etiya.recapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.UserService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.dataAccess.abstracts.UserDao;
import com.etiya.recapProject.entities.concretes.ApplicationUser;

@Service
public class UserManager implements UserService {

	@Autowired
	private UserDao userDao;
	
	public UserManager(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	@Override
	public DataResult<List<ApplicationUser>> getAll() {
		return new SuccessDataResult<List<ApplicationUser>>(this.userDao.findAll(),Messages.USERLIST);
	}

}
