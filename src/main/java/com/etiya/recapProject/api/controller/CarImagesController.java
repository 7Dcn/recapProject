package com.etiya.recapProject.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.etiya.recapProject.business.abstracts.CarImageService;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.CarImage;
import com.etiya.recapProject.entities.requests.CreateCarImageRequest;
import com.etiya.recapProject.entities.requests.DeleteCarImageRequest;
import com.etiya.recapProject.entities.requests.UpdateCarImageRequest;

@Service
public class CarImagesController {
	
	private CarImageService carImageService;

	@Autowired
	public CarImagesController(CarImageService carImageService) {
		super();
		this.carImageService = carImageService;
	}

	@PostMapping("/add")
	Result add(@Valid @RequestBody CreateCarImageRequest createCarImageRequest) {

		return this.carImageService.add(createCarImageRequest);
	}

	@PostMapping("/update")
	Result update(@Valid @RequestBody UpdateCarImageRequest updateCarImageRequest) {

		return this.carImageService.update(updateCarImageRequest);
	}

	@PutMapping("/delete")
	Result update(@Valid @RequestBody DeleteCarImageRequest deleteCarImageRequest) {

		return this.carImageService.delete(deleteCarImageRequest);
	}

	@GetMapping("/getall")
	DataResult<List<CarImage>> getAll() {
		return this.carImageService.getAll();
	}

	@GetMapping("/getImagesWithCarId")
	public DataResult<List<CarImage>> getImagesWithCarId(int carId) {

		return this.carImageService.getImagesWithCarId(carId);
	}
}
