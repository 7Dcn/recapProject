package com.etiya.recapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.CarService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.CarDao;
import com.etiya.recapProject.entities.concretes.Brand;
import com.etiya.recapProject.entities.concretes.Car;
import com.etiya.recapProject.entities.concretes.Color;
import com.etiya.recapProject.entities.dtos.CarDetailDto;
import com.etiya.recapProject.entities.requests.carRequest.CreateCarRequest;
import com.etiya.recapProject.entities.requests.carRequest.DeleteCarRequest;
import com.etiya.recapProject.entities.requests.carRequest.UpdateCarRequest;

@Service
public class CarManager implements CarService {

	private CarDao carDao;

	@Autowired
	public CarManager(CarDao carDao) {
		super();
		this.carDao = carDao;
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {
		
		Brand brand = new Brand();
		brand.setId(createCarRequest.getBrandId());
		
		Color color = new Color();
		color.setId(createCarRequest.getColorId());
		
		Car car = new Car();
		car.setCarName(createCarRequest.getCarName());
		car.setDailyPrice(createCarRequest.getDailyPrice());
		car.setDescription(createCarRequest.getDescription());
		car.setModelYear(createCarRequest.getModelYear());
		car.setFindexPoint(createCarRequest.getFindexPoint());
		car.setBrand(brand);
		car.setColor(color);
		
		this.carDao.save(car);
		return new SuccessResult(Messages.CARADD);
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {
		Brand brand = new Brand();
		brand.setId(updateCarRequest.getBrandId());
		
		Color color = new Color();
		color.setId(updateCarRequest.getColorId());
		
		Car car = this.carDao.getById(updateCarRequest.getId());
		car.setCarName(updateCarRequest.getCarName());
		car.setDailyPrice(updateCarRequest.getDailyPrice());
		car.setDescription(updateCarRequest.getDescription());
		car.setModelYear(updateCarRequest.getModelYear());
		car.setFindexPoint(updateCarRequest.getFindexPoint());
		car.setBrand(brand);
		car.setColor(color);
		
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
	public DataResult<List<Car>> getAll() {
		return new SuccessDataResult<List<Car>>(this.carDao.findAll(), Messages.CARLIST);
	}

	@Override
	public DataResult<Car> findById(int id) {
		return new SuccessDataResult<Car>(this.carDao.findById(id).get());
	}

	@Override
	public DataResult<List<CarDetailDto>> getCarsWithBrandAndColorDetails() {
		List<CarDetailDto> cars = this.carDao.getCarsWithBrandAndColorDetails();
		return new SuccessDataResult<List<CarDetailDto>>(cars, Messages.CARLIST);
	}
	
	@Override
	public DataResult<List<Car>> getCarsByBrand(int brandId) {
		List<Car> cars = this.carDao.getByBrand_Id(brandId);
		return new SuccessDataResult<List<Car>>(cars, Messages.CARLIST);
	}

	@Override
	public DataResult<List<Car>> getCarsByColor(int colorId) {
		List<Car> cars = this.carDao.getByColor_Id(colorId);
		return new SuccessDataResult<List<Car>>(cars, Messages.CARLIST);
	}

	@Override
	public DataResult<Car> getByCarName(String carName) {
		return new SuccessDataResult<Car>(this.carDao.getByCarName(carName));
	}
	
	@Override
	public DataResult<List<Car>> getAvailableCars() {
		
		List<Car> cars = this.carDao.findAll();
		
		List<Car> carsInMaintenance = this.carDao.findByCarMaintenances_ReturnStatus(false);
		
		List<Car> rentedCars = this.carDao.findByRentals_ReturnStatus(false);
		
		cars.removeAll(carsInMaintenance);
		
		cars.removeAll(rentedCars);
		
		return new SuccessDataResult<List<Car>>(cars, Messages.CARLIST);
	}
	
}
