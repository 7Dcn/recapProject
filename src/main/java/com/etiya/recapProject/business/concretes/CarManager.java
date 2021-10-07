package com.etiya.recapProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.CarService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.CarDao;
import com.etiya.recapProject.entities.concretes.Car;
import com.etiya.recapProject.entities.dtos.CarDetailDto;
import com.etiya.recapProject.entities.dtos.CarDto;
import com.etiya.recapProject.entities.requests.carRequest.CreateCarRequest;
import com.etiya.recapProject.entities.requests.carRequest.DeleteCarRequest;
import com.etiya.recapProject.entities.requests.carRequest.UpdateCarRequest;

@Service
public class CarManager implements CarService {

	private CarDao carDao;
	private ModelMapper modelMapper;

	@Autowired
	public CarManager(CarDao carDao, ModelMapper modelMapper) {
		super();
		this.carDao = carDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {

		Car car = modelMapper.map(createCarRequest, Car.class);
		this.carDao.save(car);

		return new SuccessResult(Messages.CARADD);
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {

		Car car = modelMapper.map(updateCarRequest, Car.class);
		this.carDao.save(car);

		return new SuccessResult(Messages.CARUPDATE);
	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) {
		Car car = this.carDao.getById(deleteCarRequest.getId());

		this.carDao.delete(car);
		return new SuccessResult(Messages.CARDELETE);
	}

	@Override
	public DataResult<List<CarDto>> getAll() {
		List<Car> cars = this.carDao.findAll();

		List<CarDto> result = cars.stream().map(car -> modelMapper.map(car, CarDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<CarDto>>(result, Messages.CARLIST);
	}

	@Override
	public DataResult<CarDto> findById(int id) {
		Car car = this.carDao.getById(id);
		CarDto carDto = modelMapper.map(car, CarDto.class);

		return new SuccessDataResult<CarDto>(carDto);
	}

	@Override
	public DataResult<List<CarDetailDto>> getCarsWithBrandAndColorDetails() {
		List<CarDetailDto> cars = this.carDao.getCarsWithBrandAndColorDetails();
		return new SuccessDataResult<List<CarDetailDto>>(cars, Messages.CARLIST);
	}

	@Override
	public DataResult<List<CarDto>> getCarsByBrand(int brandId) {
		List<Car> cars = this.carDao.getByBrand_Id(brandId);
		List<CarDto> result = cars.stream().map(car -> modelMapper.map(car, CarDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<CarDto>>(result, Messages.CARLIST);
	}

	@Override
	public DataResult<List<CarDto>> getCarsByColor(int colorId) {
		List<Car> cars = this.carDao.getByColor_Id(colorId);
		List<CarDto> result = cars.stream().map(car -> modelMapper.map(car, CarDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<CarDto>>(result, Messages.CARLIST);
	}

	@Override
	public DataResult<CarDto> getByCarName(String name) {
		Car car = this.carDao.getByName(name);
		CarDto carDto = modelMapper.map(car, CarDto.class);

		return new SuccessDataResult<CarDto>(carDto);
	}

	@Override
	public DataResult<List<CarDto>> getAvailableCars() {

		List<Car> cars = this.carDao.findAll();

		List<Car> carsInMaintenance = this.carDao.findByCarMaintenances_ReturnStatus(false);

		List<Car> rentedCars = this.carDao.findByRentals_ReturnStatus(false);

		cars.removeAll(carsInMaintenance);

		cars.removeAll(rentedCars);

		List<CarDto> result = cars.stream().map(car -> modelMapper.map(car, CarDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<CarDto>>(result, Messages.CARLIST);
	}

	@Override
	public DataResult<List<CarDto>> findCarsByCityName(String cityName) {

		List<Car> cars = this.carDao.findByCityName(cityName);
		List<CarDto> result = cars.stream().map(car -> modelMapper.map(car, CarDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CarDto>>(result, Messages.CARLIST);
	}

	@Override
	public DataResult<List<CarDto>> getAllMappedDto() {
		List<Car> cars = this.carDao.findAll();

		List<CarDto> result = cars.stream().map(this::convertToDto).collect(Collectors.toList());

		return new SuccessDataResult<List<CarDto>>(result, Messages.CARLIST);
	}

	private CarDto convertToDto(Car car) {
		CarDto carDto = modelMapper.map(car, CarDto.class);
		return carDto;
	}

}
