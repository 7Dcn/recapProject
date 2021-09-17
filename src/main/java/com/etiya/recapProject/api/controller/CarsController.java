package com.etiya.recapProject.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.recapProject.business.abstracts.CarService;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.Car;
import com.etiya.recapProject.entities.dtos.CarDetailDto;
import com.etiya.recapProject.entities.requests.CreateCarRequest;
import com.etiya.recapProject.entities.requests.DeleteCarRequest;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("api/cars")
public class CarsController {
	private CarService carService;

	@Autowired
	public CarsController(CarService carService) {
		super();
		this.carService = carService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateCarRequest createCarRequest) {
		return this.carService.add(createCarRequest);
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody Car car) {
		return this.carService.update(car);
	}
	
	@PutMapping("/delete")
	public Result delete(@RequestBody DeleteCarRequest deleteCarRequest) {
		return this.carService.delete(deleteCarRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<Car>> getAll() {
		return this.carService.getAll();
	}
	
	@GetMapping("/getallbyid")
	public DataResult<Car> getById(int id) {
		return this.carService.findById(id);
	}
	
	@GetMapping("/getcardetails")
	public DataResult<List<CarDetailDto>> getCarsDetails() {
		return this.carService.getCarsWithBrandAndColorDetails();
	}
	
}