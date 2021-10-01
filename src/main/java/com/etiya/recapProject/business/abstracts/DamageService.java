package com.etiya.recapProject.business.abstracts;

import java.util.List;

import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.Damage;

import com.etiya.recapProject.entities.requests.damageRequest.CreateDamageRequest;
import com.etiya.recapProject.entities.requests.damageRequest.DeleteDamageRequest;
import com.etiya.recapProject.entities.requests.damageRequest.UpdateDamageRequest;

public interface DamageService {
	Result add(CreateDamageRequest createDamageRequest);

	Result update(UpdateDamageRequest updateDamageRequest);

	Result delete(DeleteDamageRequest deleteDamageRequest);

	DataResult<List<Damage>> getAll();

	DataResult<List<Damage>> findByCarId(int carId);
}
