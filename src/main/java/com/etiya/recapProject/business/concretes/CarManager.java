package com.etiya.recapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.CarService;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.CarDao;
import com.etiya.recapProject.entities.concretes.Car;
import com.etiya.recapProject.entities.dtos.CarDetailDto;

@Service
public class CarManager implements CarService {

	private CarDao carDao;

	@Autowired
	public CarManager(CarDao carDao) {
		super();
		this.carDao = carDao;
	}

	@Override
	public Result add(Car car) {
		this.carDao.save(car);
		return new SuccessResult("Araba eklendi.");
	}

	@Override
	public Result update(Car car) {
		this.carDao.save(car);
		return new SuccessResult("Araba bilgileri güncellendi.");
	}

	@Override
	public Result delete(Car car) {
		this.carDao.delete(car);
		return new SuccessResult("Araba silindi.");
	}

	@Override
	public DataResult<List<Car>> getAll() {
		return new SuccessDataResult<List<Car>>(this.carDao.findAll(), "Arabalar listelendi");
	}

	@Override
	public DataResult<Car> findById(int id) {
		return new SuccessDataResult<Car>(this.carDao.findById(id).get());
	}

	@Override
	public DataResult<List<CarDetailDto>> getCartWithBrandAndColorDetails() {
		List<CarDetailDto> cars = this.carDao.getCartWithBrandAndColorDetails();
		return new SuccessDataResult<List<CarDetailDto>>(cars, "Arabalar başarıyla listelendi");
	}

}
