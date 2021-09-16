package com.etiya.recapProject.business.abstracts;

import java.util.List;

import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.Car;
import com.etiya.recapProject.entities.dtos.CarDetailDto;

public interface CarService {
	Result add(Car car);

	Result update(Car car);

	Result delete(Car car);

	DataResult<List<Car>> getAll();
	
	DataResult<Car> findById(int id);
	
	DataResult<List<CarDetailDto>> getCartWithBrandAndColorDetails();
}
