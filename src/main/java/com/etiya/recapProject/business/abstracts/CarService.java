package com.etiya.recapProject.business.abstracts;

import java.util.List;

import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.dtos.CarDetailDto;
import com.etiya.recapProject.entities.dtos.CarDto;
import com.etiya.recapProject.entities.requests.carRequest.CreateCarRequest;
import com.etiya.recapProject.entities.requests.carRequest.DeleteCarRequest;
import com.etiya.recapProject.entities.requests.carRequest.UpdateCarRequest;

public interface CarService {
	Result add(CreateCarRequest createCarRequest);

	Result update(UpdateCarRequest updateCarRequest);

	Result delete(DeleteCarRequest deleteCarRequest);

	DataResult<List<CarDto>> getAll();

	DataResult<CarDto> findById(int id);

	DataResult<List<CarDetailDto>> getCarsWithBrandAndColorDetails();
	
	DataResult<List<CarDto>> getCarsByBrand(int brandId);
	
	DataResult<List<CarDto>> getCarsByColor(int colorId);
	
	DataResult<CarDto> getByCarName(String name);
	
	DataResult<List<CarDto>> getAvailableCars();
	
	DataResult<List<CarDto>> findCarsByCityName(String cityName);
	
	DataResult<List<CarDto>> getAllMappedDto();
}
