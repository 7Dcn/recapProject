package com.etiya.recapProject.business.abstracts;

import java.util.List;

import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.CarImage;
import com.etiya.recapProject.entities.requests.CreateCarImageRequest;
import com.etiya.recapProject.entities.requests.DeleteCarImageRequest;
import com.etiya.recapProject.entities.requests.UpdateCarImageRequest;

public interface CarImageService {
	Result add(CreateCarImageRequest createCarImageRequest);

	Result update(UpdateCarImageRequest updateCarImageRequest);

	Result delete(DeleteCarImageRequest deleteCarImageRequest);

	DataResult<List<CarImage>> getAll();

	DataResult<List<CarImage>> getImagesWithCarId(int id);
}
