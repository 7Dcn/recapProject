package com.etiya.recapProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.ApplicationUserService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.dataAccess.abstracts.ApplicationUserDao;
import com.etiya.recapProject.entities.abstracts.ApplicationUser;
import com.etiya.recapProject.entities.dtos.ApplicationUserDto;

@Service
public class ApplicationUserManager implements ApplicationUserService {

	@Autowired
	private ApplicationUserDao userDao;
	private ModelMapper modelMapper;

	public ApplicationUserManager(ApplicationUserDao userDao, ModelMapper modelMapper) {
		super();
		this.userDao = userDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public DataResult<List<ApplicationUserDto>> getAll() {
		List<ApplicationUser> applicationUsers = this.userDao.findAll();

		List<ApplicationUserDto> result = applicationUsers.stream()
				.map(applicationUser -> modelMapper.map(applicationUser, ApplicationUserDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ApplicationUserDto>>(result, Messages.USERLIST);
	}

}
