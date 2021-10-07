package com.etiya.recapProject.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.recapProject.business.abstracts.CarService;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.dtos.CarDetailDto;
import com.etiya.recapProject.entities.dtos.CarDto;
import com.etiya.recapProject.entities.requests.carRequest.CreateCarRequest;
import com.etiya.recapProject.entities.requests.carRequest.DeleteCarRequest;
import com.etiya.recapProject.entities.requests.carRequest.UpdateCarRequest;

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
	public Result add(@Valid @RequestBody CreateCarRequest createCarRequest) {
		return this.carService.add(createCarRequest);
	}

	@PostMapping("/update")
	public Result update(@Valid @RequestBody UpdateCarRequest updateCarRequest) {
		return this.carService.update(updateCarRequest);
	}

	@PutMapping("/delete")
	public Result delete(@RequestBody DeleteCarRequest deleteCarRequest) {
		return this.carService.delete(deleteCarRequest);
	}

	@GetMapping("/getall")
	public DataResult<List<CarDto>> getAll() {
		return this.carService.getAll();
	}

	@GetMapping("/getbyid")
	public DataResult<CarDto> getById(int id) {
		return this.carService.findById(id);
	}

	@GetMapping("/getcardetails")
	public DataResult<List<CarDetailDto>> getCarsDetails() {
		return this.carService.getCarsWithBrandAndColorDetails();
	}

	@GetMapping("/getcarsbybrand")
	public DataResult<List<CarDto>> getCarsByBrand(int brandId) {
		return this.carService.getCarsByBrand(brandId);
	}

	@GetMapping("/getcarsbycolor")
	public DataResult<List<CarDto>> getCarsByColor(int colorId) {
		return this.carService.getCarsByColor(colorId);
	}

	@GetMapping("/getbyname")
	public DataResult<CarDto> getByName(String name) {
		return this.carService.getByCarName(name);
	}

	@GetMapping("/getavailablecars")
	public DataResult<List<CarDto>> getAvailableCars() {
		return this.carService.getAvailableCars();
	}

	@GetMapping("/getcarsbycityname")
	public DataResult<List<CarDto>> findCarsByCityName(String cityName) {
		return this.carService.findCarsByCityName(cityName);
	}
	
	@GetMapping("/getallmappeddto")
	public DataResult<List<CarDto>> getAllMappedDto() {
		return this.carService.getAllMappedDto();
	}
	

}