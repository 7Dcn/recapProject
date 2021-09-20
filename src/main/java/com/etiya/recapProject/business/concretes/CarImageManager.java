package com.etiya.recapProject.business.concretes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.etiya.recapProject.business.abstracts.CarImageService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.business.BusinessRules;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.ErrorResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.CarImageDao;
import com.etiya.recapProject.entities.concretes.Car;
import com.etiya.recapProject.entities.concretes.CarImage;
import com.etiya.recapProject.entities.requests.CarImageRequest.CreateCarImageRequest;
import com.etiya.recapProject.entities.requests.CarImageRequest.DeleteCarImageRequest;
import com.etiya.recapProject.entities.requests.CarImageRequest.UpdateCarImageRequest;

@Service
public class CarImageManager implements CarImageService {

	private CarImageDao carImageDao;

	@Autowired
	public CarImageManager(CarImageDao carImageDao) {
		super();
		this.carImageDao = carImageDao;
	}

	@Override
	public Result add(CreateCarImageRequest createCarImageRequest, MultipartFile file) throws IOException {
		var result = BusinessRules.run(checkImagesNumberLimit(createCarImageRequest.getCarId(), 5));
		if (result != null) {
			return result;
		}

		Car car = new Car();
		car.setId(createCarImageRequest.getCarId());
		String imagePathGuid = java.util.UUID.randomUUID().toString();

		File imageFile = new File("C:\\Etiya\\carimages\\" + imagePathGuid);

		imageFile.createNewFile();
		FileOutputStream outputImage = new FileOutputStream(imageFile);
		outputImage.write(file.getBytes());
		outputImage.close();

		CarImage carImage = new CarImage();
		carImage.setDate(LocalDate.now());
		carImage.setImagePath(imageFile.toString());
		carImage.setImageName(createCarImageRequest.getImageName());
		carImage.setCar(car);

		this.carImageDao.save(carImage);

		return new SuccessResult(Messages.CARIMAGEADD);
	}

	@Override
	public Result update(UpdateCarImageRequest updateCarImageRequest, MultipartFile file)throws IOException {
		var result = BusinessRules.run(checkImagesNumberLimit(updateCarImageRequest.getCarId(), 5));
		if (result != null) {
			return result;
		}
		
		String imagePathGuid = java.util.UUID.randomUUID().toString();

		File imageFile = new File("C:\\Etiya\\carimages\\" + imagePathGuid);

		imageFile.createNewFile();
		FileOutputStream outputImage = new FileOutputStream(imageFile);
		outputImage.write(file.getBytes());
		outputImage.close();
		
		Car car = new Car();
		car.setId(updateCarImageRequest.getCarId());

		CarImage carImage = new CarImage();
		carImage.setId(updateCarImageRequest.getCarId());
		carImage.setDate(LocalDate.now());
		carImage.setImagePath(imageFile.toString());
		carImage.setImageName(updateCarImageRequest.getImageName());
		carImage.setCar(car);

		this.carImageDao.save(carImage);
		
		return new SuccessResult(Messages.CARIMAGEUPDATE);
	}

	@Override
	public Result delete(DeleteCarImageRequest deleteCarImageRequest) {
		Car car = new Car();
		car.setId(deleteCarImageRequest.getCarId());

		this.carImageDao.deleteById(car.getId());
		return new SuccessResult(Messages.CARIMAGEDELETE);
	}

	@Override
	public DataResult<List<CarImage>> getAll() {
		return new SuccessDataResult<List<CarImage>>(this.carImageDao.findAll(), Messages.CARIMAGELIST);
	}

	@Override
	public DataResult<List<CarImage>> getImagesWithCarId(int id) {
		if(!checkImageIsEmpty(id).isSuccess()) {
			File defaultImagePath = new File("C:\\Etiya\\carimages\\default\\default.jpg");
			System.out.println(defaultImagePath);
			return new SuccessDataResult<List<CarImage>>(Messages.CARIMAGEDEFAULT);
		}
		return new SuccessDataResult<List<CarImage>>(this.carImageDao.getByCar_Id(id), Messages.CARIMAGELIST);
	}

	private Result checkImagesNumberLimit(int carId, int limit) {
		if (this.carImageDao.countCarImageByCar_Id(carId) >= limit) {
			return new ErrorResult(Messages.CARIMAGELIMITERROR);
		}
		return new SuccessResult();
	}

	private Result checkImageIsEmpty(int carId) {
		if (this.carImageDao.getImagePathByCar_Id(carId)==null) {
			return new ErrorResult();
		}
		return new SuccessResult();
	}

}
