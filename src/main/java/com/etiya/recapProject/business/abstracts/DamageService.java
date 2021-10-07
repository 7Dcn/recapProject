package com.etiya.recapProject.business.abstracts;

import java.util.List;

import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.dtos.DamageDto;
import com.etiya.recapProject.entities.requests.damageRequest.CreateDamageRequest;
import com.etiya.recapProject.entities.requests.damageRequest.DeleteDamageRequest;
import com.etiya.recapProject.entities.requests.damageRequest.UpdateDamageRequest;

public interface DamageService {
	Result add(CreateDamageRequest createDamageRequest);

	Result update(UpdateDamageRequest updateDamageRequest);

	Result delete(DeleteDamageRequest deleteDamageRequest);

	DataResult<List<DamageDto>> getAll();

	DataResult<List<DamageDto>> findByCarId(int carId);
}
