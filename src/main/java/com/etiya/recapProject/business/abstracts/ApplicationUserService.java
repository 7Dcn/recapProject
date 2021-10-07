package com.etiya.recapProject.business.abstracts;

import java.util.List;

import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.entities.dtos.ApplicationUserDto;

public interface ApplicationUserService {
	DataResult<List<ApplicationUserDto>> getAll();
}
