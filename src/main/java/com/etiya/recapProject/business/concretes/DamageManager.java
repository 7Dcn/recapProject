package com.etiya.recapProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.DamageService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.DamageDao;
import com.etiya.recapProject.entities.concretes.Damage;
import com.etiya.recapProject.entities.dtos.DamageDto;
import com.etiya.recapProject.entities.requests.damageRequest.CreateDamageRequest;
import com.etiya.recapProject.entities.requests.damageRequest.DeleteDamageRequest;
import com.etiya.recapProject.entities.requests.damageRequest.UpdateDamageRequest;

@Service
public class DamageManager implements DamageService {

	private DamageDao damageDao;
	private ModelMapper modelMapper;

	@Autowired
	public DamageManager(DamageDao damageDao, ModelMapper modelMapper) {
		super();
		this.damageDao = damageDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public Result add(CreateDamageRequest createDamageRequest) {

		Damage damage = modelMapper.map(createDamageRequest, Damage.class);

		this.damageDao.save(damage);

		return new SuccessResult(Messages.DAMAGEADD);
	}

	@Override
	public Result update(UpdateDamageRequest updateDamageRequest) {

		Damage damage = modelMapper.map(updateDamageRequest, Damage.class);

		this.damageDao.save(damage);

		return new SuccessResult(Messages.DAMAGEUPDATE);
	}

	@Override
	public Result delete(DeleteDamageRequest deleteDamageRequest) {

		Damage damage = this.damageDao.getById(deleteDamageRequest.getId());

		this.damageDao.delete(damage);
		return new SuccessResult(Messages.DAMAGEDELETE);
	}

	@Override
	public DataResult<List<DamageDto>> getAll() {
		List<Damage> damages = this.damageDao.findAll();
		List<DamageDto> result = damages.stream().map(damage -> modelMapper.map(damage, DamageDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<DamageDto>>(result, Messages.DAMAGELIST);
	}

	@Override
	public DataResult<List<DamageDto>> findByCarId(int carId) {
		List<Damage> damages = this.damageDao.findByCar_Id(carId);
		List<DamageDto> result = damages.stream().map(damage -> modelMapper.map(damage, DamageDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<DamageDto>>(result, Messages.DAMAGELIST);
	}

}
