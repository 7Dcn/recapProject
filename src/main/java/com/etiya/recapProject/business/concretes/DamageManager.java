package com.etiya.recapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.DamageService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.CarDao;
import com.etiya.recapProject.dataAccess.abstracts.DamageDao;
import com.etiya.recapProject.entities.concretes.Car;
import com.etiya.recapProject.entities.concretes.Damage;
import com.etiya.recapProject.entities.requests.damageRequest.CreateDamageRequest;
import com.etiya.recapProject.entities.requests.damageRequest.DeleteDamageRequest;
import com.etiya.recapProject.entities.requests.damageRequest.UpdateDamageRequest;

@Service
public class DamageManager implements DamageService {

	DamageDao damageDao;
	CarDao carDao;

	@Autowired
	public DamageManager(DamageDao damageDao, CarDao carDao) {
		super();
		this.damageDao = damageDao;
		this.carDao = carDao;
	}

	@Override
	public Result add(CreateDamageRequest createDamageRequest) {

		Car car = this.carDao.getById(createDamageRequest.getCarId());

		Damage damage = new Damage();
		damage.setInformation(createDamageRequest.getInformation());

		damage.setCar(car);

		this.damageDao.save(damage);

		return new SuccessResult(Messages.DAMAGEADD);
	}

	@Override
	public Result update(UpdateDamageRequest updateDamageRequest) {
		Car car = this.carDao.getById(updateDamageRequest.getCarId());

		Damage damage = this.damageDao.getById(updateDamageRequest.getId());
		damage.setInformation(updateDamageRequest.getInformation());

		damage.setCar(car);

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
	public DataResult<List<Damage>> getAll() {

		return new SuccessDataResult<List<Damage>>(this.damageDao.findAll(), Messages.DAMAGELIST);
	}

	@Override
	public DataResult<List<Damage>> findByCarId(int carId) {
		List<Damage> damages = this.damageDao.findByCar_Id(carId);

		return new SuccessDataResult<List<Damage>>(damages, Messages.DAMAGELIST);
	}

}
