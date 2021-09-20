package com.etiya.recapProject.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.etiya.recapProject.business.abstracts.CarImageService;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.CarImage;
import com.etiya.recapProject.entities.requests.CarImageRequest.CreateCarImageRequest;
import com.etiya.recapProject.entities.requests.CarImageRequest.DeleteCarImageRequest;
import com.etiya.recapProject.entities.requests.CarImageRequest.UpdateCarImageRequest;

@RestController
@RequestMapping("api/carimages")
public class CarImagesController {
	
	private CarImageService carImageService;

	@Autowired
	public CarImagesController(CarImageService carImageService) {
		super();
		this.carImageService = carImageService;
	}

	@PostMapping("/add")
	Result add(@Valid CreateCarImageRequest createCarImageRequest, MultipartFile file) throws IOException {

		return this.carImageService.add(createCarImageRequest, file);
	}

	@PostMapping("/update")
	Result update(@Valid @RequestBody UpdateCarImageRequest updateCarImageRequest, MultipartFile file)throws IOException {

		return this.carImageService.update(updateCarImageRequest, file);
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
