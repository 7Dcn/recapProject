package com.etiya.recapProject.business.concretes;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.CarMaintenanceService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.business.BusinessRules;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.ErrorResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.CarMaintenanceDao;
import com.etiya.recapProject.dataAccess.abstracts.RentalDao;
import com.etiya.recapProject.entities.concretes.CarMaintenance;
import com.etiya.recapProject.entities.concretes.Rental;
import com.etiya.recapProject.entities.requests.carMaintenanceRequest.CreateCarMaintenanceRequest;
import com.etiya.recapProject.entities.requests.carMaintenanceRequest.DeleteCarMaintenanceRequest;
import com.etiya.recapProject.entities.requests.carMaintenanceRequest.UpdateCarMaintenanceRequest;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

	private CarMaintenanceDao carMaintenanceDao;
	private RentalDao rentalDao;
	private ModelMapper modelMapper;

	@Autowired
	public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, RentalDao rentalDao, ModelMapper modelMapper) {
		super();
		this.carMaintenanceDao = carMaintenanceDao;
		this.rentalDao = rentalDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) {

		var result = BusinessRules.run(checkIfCarRented(createCarMaintenanceRequest.getCarId()));

		if (result != null) {
			return result;
		}

		CarMaintenance carMaintenance = modelMapper.map(createCarMaintenanceRequest, CarMaintenance.class);
		this.carMaintenanceDao.save(carMaintenance);

		return new SuccessResult(Messages.CARMAINTENANCEADD);

	}

	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) {
		CarMaintenance carMaintenance = modelMapper.map(updateCarMaintenanceRequest, CarMaintenance.class);
		this.carMaintenanceDao.save(carMaintenance);

		return new SuccessResult(Messages.CARMAINTENANCEUPDATE);
	}

	@Override
	public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) {
		CarMaintenance carMaintenance = this.carMaintenanceDao.getById(deleteCarMaintenanceRequest.getId());

		this.carMaintenanceDao.delete(carMaintenance);

		return new SuccessResult(Messages.CARMAINTENANCEDELETE);
	}

	@Override
	public DataResult<List<CarMaintenance>> getAll() {
		return new SuccessDataResult<List<CarMaintenance>>(this.carMaintenanceDao.findAll(),
				Messages.CARMAINTENANCELIST);
	}

	private Result checkIfCarRented(int carId) {

		if (this.rentalDao.getByCar_Id(carId).size() != 0) {
			Rental rental = this.rentalDao.getByCar_Id(carId).get(this.rentalDao.getByCar_Id(carId).size() - 1);

			if (rental.getReturnDate() == null) {
				return new ErrorResult(Messages.CARMAINTENANCERENTALERROR);
			}
		}
		return new SuccessResult();
	}

}
