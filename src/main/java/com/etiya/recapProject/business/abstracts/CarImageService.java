package com.etiya.recapProject.business.abstracts;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.CarImage;
import com.etiya.recapProject.entities.requests.CarImageRequest.CreateCarImageRequest;
import com.etiya.recapProject.entities.requests.CarImageRequest.DeleteCarImageRequest;
import com.etiya.recapProject.entities.requests.CarImageRequest.UpdateCarImageRequest;

public interface CarImageService {
	Result add(CreateCarImageRequest createCarImageRequest, MultipartFile file)throws IOException;

	Result update(UpdateCarImageRequest updateCarImageRequest, MultipartFile file)throws IOException;

	Result delete(DeleteCarImageRequest deleteCarImageRequest);

	DataResult<List<CarImage>> getAll();

	DataResult<List<CarImage>> getImagesWithCarId(int id);
}
