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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.etiya.recapProject.business.abstracts.CarImageService;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.dtos.CarImageDto;
import com.etiya.recapProject.entities.requests.carImageRequest.CreateCarImageRequest;
import com.etiya.recapProject.entities.requests.carImageRequest.DeleteCarImageRequest;
import com.etiya.recapProject.entities.requests.carImageRequest.UpdateCarImageRequest;

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
	Result add(@Valid @RequestParam("carId") int carId, @RequestParam("imageName") String imageName, MultipartFile file)
			throws IOException {

		CreateCarImageRequest createCarImageRequest = new CreateCarImageRequest();

		createCarImageRequest.setCarId(carId);
		createCarImageRequest.setFile(file);
		createCarImageRequest.setImageName(imageName);

		return this.carImageService.add(createCarImageRequest);
	}

	@PostMapping("/update")
	Result update(@Valid @RequestParam("id") int id, @RequestParam("carId") int carId,
			@RequestParam("imageName") String imageName, MultipartFile file) throws IOException {

		UpdateCarImageRequest updateCarImageRequest = new UpdateCarImageRequest();
		updateCarImageRequest.setId(id);
		updateCarImageRequest.setCarId(carId);
		updateCarImageRequest.setFile(file);
		updateCarImageRequest.setImageName(imageName);
		
		return this.carImageService.update(updateCarImageRequest);
	}

	@PutMapping("/delete")
	Result delete(@Valid @RequestBody DeleteCarImageRequest deleteCarImageRequest) {

		return this.carImageService.delete(deleteCarImageRequest);
	}

	@GetMapping("/getall")
	DataResult<List<CarImageDto>> getAll() {
		return this.carImageService.getAll();
	}

	@GetMapping("/getImagesWithCarId")
	public DataResult<List<CarImageDto>> getImagesWithCarId(int carId) {

		return this.carImageService.getImagesWithCarId(carId);
	}
}
