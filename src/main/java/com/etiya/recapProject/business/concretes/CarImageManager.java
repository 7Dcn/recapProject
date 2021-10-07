package com.etiya.recapProject.business.concretes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
import com.etiya.recapProject.entities.concretes.CarImage;
import com.etiya.recapProject.entities.dtos.CarImageDto;
import com.etiya.recapProject.entities.requests.carImageRequest.CreateCarImageRequest;
import com.etiya.recapProject.entities.requests.carImageRequest.DeleteCarImageRequest;
import com.etiya.recapProject.entities.requests.carImageRequest.UpdateCarImageRequest;

@Service
public class CarImageManager implements CarImageService {

	private CarImageDao carImageDao;
	private ModelMapper modelMapper;

	@Autowired
	public CarImageManager(CarImageDao carImageDao, ModelMapper modelMapper) {
		super();
		this.carImageDao = carImageDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public Result add(CreateCarImageRequest createCarImageRequest) throws IOException {
		var result = BusinessRules.run(checkImagesNumberLimit(createCarImageRequest.getCarId(), 5),
				checkImageType(createCarImageRequest.getFile()));
		if (result != null) {
			return result;
		}

		CarImage carImage = modelMapper.map(createCarImageRequest, CarImage.class);
		carImage.setDate(LocalDate.now());
		carImage.setImagePath(generateImage(createCarImageRequest.getFile()).toString());
		this.carImageDao.save(carImage);

		return new SuccessResult(Messages.CARIMAGEADD);
	}

	@Override
	public Result update(UpdateCarImageRequest updateCarImageRequest) throws IOException {
		var result = BusinessRules.run(checkImagesNumberLimit(updateCarImageRequest.getCarId(), 5));
		if (result != null) {
			return result;
		}

		CarImage carImage = modelMapper.map(updateCarImageRequest, CarImage.class);
		carImage.setDate(LocalDate.now());
		carImage.setImagePath(generateImage(updateCarImageRequest.getFile()).toString());
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
	public DataResult<List<CarImageDto>> getAll() {
		List<CarImage> carImages = this.carImageDao.findAll();

		List<CarImageDto> result = carImages.stream().map(carImage -> modelMapper.map(carImage, CarImageDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CarImageDto>>(result, Messages.CARIMAGELIST);
	}

	@Override
	public DataResult<List<CarImageDto>> getImagesWithCarId(int carId) {

		return new SuccessDataResult<List<CarImageDto>>(this.ifCarImageIsNullAddDefault(carId));
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

	private List<CarImageDto> ifCarImageIsNullAddDefault(int carId) {
		if (this.carImageDao.getByCar_Id(carId).isEmpty()) {

			CarImageDto carImageDto = new CarImageDto();
			carImageDto.setImagePath(ImagePath.CARDEFAULTIMAGEPATH);

			List<CarImageDto> carImages = new ArrayList<CarImageDto>();
			carImages.add(carImageDto);

			return carImages;

		}
		List<CarImage> carImages = this.carImageDao.findAll();

		List<CarImageDto> result = carImages.stream().map(carImage -> modelMapper.map(carImage, CarImageDto.class))
				.collect(Collectors.toList());

		return new ArrayList<CarImageDto>(result);
	}

	private File generateImage(MultipartFile file) throws IOException {

		String imagePathGuid = java.util.UUID.randomUUID().toString();

		File imageFile = new File(ImagePath.CARIMAGEPATH + imagePathGuid + "."
				+ file.getContentType().substring(file.getContentType().indexOf("/") + 1));

		imageFile.createNewFile();
		FileOutputStream outputImage = new FileOutputStream(imageFile);
		outputImage.write(file.getBytes());
		outputImage.close();

		return imageFile;
	}

}
