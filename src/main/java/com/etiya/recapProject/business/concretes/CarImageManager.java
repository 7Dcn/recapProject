package com.etiya.recapProject.business.concretes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.etiya.recapProject.business.abstracts.CarImageService;
import com.etiya.recapProject.business.constants.ImagePath;
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
import com.etiya.recapProject.entities.requests.carImageRequest.CreateCarImageRequest;
import com.etiya.recapProject.entities.requests.carImageRequest.DeleteCarImageRequest;
import com.etiya.recapProject.entities.requests.carImageRequest.UpdateCarImageRequest;

@Service
public class CarImageManager implements CarImageService {

	private CarImageDao carImageDao;

	@Autowired
	public CarImageManager(CarImageDao carImageDao) {
		super();
		this.carImageDao = carImageDao;
	}

	@Override
	public Result add(CreateCarImageRequest createCarImageRequest) throws IOException {
		var result = BusinessRules.run(checkImagesNumberLimit(createCarImageRequest.getCarId(), 5),
				checkImageType(createCarImageRequest.getFile()));
		if (result != null) {
			return result;
		}

		Car car = new Car();
		car.setId(createCarImageRequest.getCarId());

		String imagePathGuid = java.util.UUID.randomUUID().toString();

		File imageFile = new File(ImagePath.CARIMAGEPATH + imagePathGuid + "." + createCarImageRequest.getFile()
				.getContentType().substring(createCarImageRequest.getFile().getContentType().indexOf("/") + 1));

		imageFile.createNewFile();
		FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
		fileOutputStream.write(createCarImageRequest.getFile().getBytes());
		fileOutputStream.close();

		CarImage carImage = new CarImage();
		carImage.setDate(LocalDate.now());
		carImage.setImagePath(imageFile.toString());
		carImage.setImageName(createCarImageRequest.getImageName());
		carImage.setCar(car);

		this.carImageDao.save(carImage);

		return new SuccessResult(Messages.CARIMAGEADD);
	}

	@Override
	public Result update(UpdateCarImageRequest updateCarImageRequest) throws IOException {
		var result = BusinessRules.run(checkImagesNumberLimit(updateCarImageRequest.getCarId(), 5));
		if (result != null) {
			return result;
		}

		String imagePathGuid = java.util.UUID.randomUUID().toString();

		File imageFile = new File(ImagePath.CARIMAGEPATH + imagePathGuid + "." + updateCarImageRequest.getFile()
				.getContentType().substring(updateCarImageRequest.getFile().getContentType().indexOf("/") + 1));

		imageFile.createNewFile();
		FileOutputStream outputImage = new FileOutputStream(imageFile);
		outputImage.write(updateCarImageRequest.getFile().getBytes());
		outputImage.close();

		Car car = new Car();
		car.setId(updateCarImageRequest.getCarId());

		CarImage carImage = this.carImageDao.getById(updateCarImageRequest.getId());
		carImage.setDate(LocalDate.now());
		carImage.setImagePath(imageFile.toString());
		carImage.setImageName(updateCarImageRequest.getImageName());
		carImage.setCar(car);

		this.carImageDao.save(carImage);

		return new SuccessResult(Messages.CARIMAGEUPDATE);
	}

	@Override
	public Result delete(DeleteCarImageRequest deleteCarImageRequest) {
		CarImage carImage = this.carImageDao.getById(deleteCarImageRequest.getId());

		this.carImageDao.delete(carImage);
		return new SuccessResult(Messages.CARIMAGEDELETE);
	}

	@Override
	public DataResult<List<CarImage>> getAll() {
		return new SuccessDataResult<List<CarImage>>(this.carImageDao.findAll(), Messages.CARIMAGELIST);
	}

	@Override
	public DataResult<List<CarImage>> getImagesWithCarId(int carId) {
		return new SuccessDataResult<List<CarImage>>(this.ifCarImageIsNullAddDefault(carId));
	}

	private Result checkImagesNumberLimit(int carId, int limit) {
		if (this.carImageDao.countCarImageByCar_Id(carId) >= limit) {
			return new ErrorResult(Messages.CARIMAGELIMITERROR);
		}
		return new SuccessResult();
	}

	private Result checkImageIsNull(MultipartFile file) {
		if (file == null || file.isEmpty() || file.getSize() == 0) {
			return new ErrorResult(Messages.CARIMAGEEMPTY);
		}
		return new SuccessResult();
	}

//	private Result checkImageType(MultipartFile file) throws IOException {
//		if (!this.checkImageIsNull(file).isSuccess()) {
//			return new ErrorResult(this.checkImageIsNull(file).getMessage());
//		}
//		ImageInputStream imageInputStream = ImageIO.createImageInputStream(file);
//
//		Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageInputStream);
//
//		while (imageReaders.hasNext()) {
//			ImageReader reader = (ImageReader) imageReaders.next();
//			System.out.printf("formatName: %s%n", reader.getFormatName());
//			if (reader.getFormatName() != "jpg" || reader.getFormatName() != "jpeg"
//					|| reader.getFormatName() != "png") {
//				return new ErrorResult(Messages.CARIMAGETYPEERROR);
//			}
//
//		}
//		return new SuccessResult();
//	}

	private Result checkImageType(MultipartFile file) {
		if (this.checkImageIsNull(file).isSuccess() == false) {
			return new ErrorResult(this.checkImageIsNull(file).getMessage());
		}
		if (!file.getContentType().toString().substring(file.getContentType().indexOf("/") + 1).equals("jpeg")
				&& !file.getContentType().toString().substring(file.getContentType().indexOf("/") + 1).equals("jpg")
				&& !file.getContentType().toString().substring(file.getContentType().indexOf("/") + 1).equals("png")) {
			return new ErrorResult(Messages.CARIMAGETYPEERROR);
		}
		return new SuccessResult();

	}
	
	private List<CarImage> ifCarImageIsNullAddDefault(int carId) {
		if (this.carImageDao.getByCar_Id(carId).isEmpty()) {

			Car car = new Car();
			car.setId(carId);

			CarImage carImage=new CarImage();
			carImage.setCar(car);
			carImage.setImagePath(ImagePath.CARDEFAULTIMAGEPATH);
	
			List<CarImage> carImages=new ArrayList<CarImage>();
			carImages.add(carImage);
			
			return carImages;
		    
		}
		return new ArrayList<CarImage>(this.carImageDao.getByCar_Id(carId));
	}

}
