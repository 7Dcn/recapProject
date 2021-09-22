package com.etiya.recapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.CustomerFindexPointCheckService;
import com.etiya.recapProject.business.abstracts.RentalService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.business.BusinessRules;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.ErrorResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.CarDao;
import com.etiya.recapProject.dataAccess.abstracts.CorporateCustomerDao;
import com.etiya.recapProject.dataAccess.abstracts.IndividualCustomerDao;
import com.etiya.recapProject.dataAccess.abstracts.RentalDao;
import com.etiya.recapProject.entities.concretes.Car;
import com.etiya.recapProject.entities.concretes.CorporateCustomer;
import com.etiya.recapProject.entities.concretes.IndividualCustomer;
import com.etiya.recapProject.entities.concretes.Rental;
import com.etiya.recapProject.entities.dtos.RentalDetailDto;
import com.etiya.recapProject.entities.requests.rentalRequest.CreateRentalRequest;
import com.etiya.recapProject.entities.requests.rentalRequest.UpdateRentalRequest;

@Service
public class RentalManager implements RentalService {

	private RentalDao rentalDao;
	private CustomerFindexPointCheckService userFindexPointCheckService;
	private IndividualCustomerDao individualCustomerDao;
	private CorporateCustomerDao corporateCustomerDao;
	private CarDao carDao;

	@Autowired
	public RentalManager(RentalDao rentalDao, CustomerFindexPointCheckService userFindexPointCheckService,
			IndividualCustomerDao individualCustomerDao, CarDao carDao, CorporateCustomerDao corporateCustomerDao) {
		super();
		this.rentalDao = rentalDao;
		this.userFindexPointCheckService = userFindexPointCheckService;
		this.individualCustomerDao = individualCustomerDao;
		this.carDao = carDao;
		this.corporateCustomerDao = corporateCustomerDao;
	}

	@Override
	public Result addRentalForIndividualCustomer(CreateRentalRequest createRentalRequest) {
		Car car = new Car();
		car.setId(createRentalRequest.getCarId());

		IndividualCustomer individualCustomer = new IndividualCustomer();
		individualCustomer.setId(createRentalRequest.getCustomerId());

		var result = BusinessRules.run(checkCarIsReturned(createRentalRequest.getCarId()),
				checkIndiviualCustomerFindexPoint(
						this.individualCustomerDao.getById(createRentalRequest.getCustomerId()),
						this.carDao.getById(createRentalRequest.getCarId())));

		if (result != null) {
			return result;
		}

		Rental rental = new Rental();
		rental.setRentDate(createRentalRequest.getRentDate());
		rental.setCar(car);
		rental.setCustomer(individualCustomer);

		this.rentalDao.save(rental);
		return new SuccessResult(Messages.RENTALADD);

	}

	@Override
	public Result updateRentalForIndividualCustomer(UpdateRentalRequest updateRentalRequest) {

		Car car = new Car();
		car.setId(updateRentalRequest.getCarId());

		IndividualCustomer individualCustomer = new IndividualCustomer();
		individualCustomer.setId(updateRentalRequest.getCustomerId());

		var result = BusinessRules.run(checkIndiviualCustomerFindexPoint(
				this.individualCustomerDao.getById(updateRentalRequest.getCustomerId()),
				this.carDao.getById(updateRentalRequest.getCarId())));
		if (result != null) {
			return result;
		}

		Rental rental = this.rentalDao.getById(updateRentalRequest.getId());
		rental.setRentDate(updateRentalRequest.getRentDate());
		rental.setReturnDate(updateRentalRequest.getReturnDate());
		rental.setCar(car);
		rental.setCustomer(individualCustomer);
		rental.setReturnStatus(updateRentalRequest.isRentStatus());

		this.rentalDao.save(rental);
		return new SuccessResult(Messages.RENTALUPDATE);
	}

	@Override
	public Result addRentalForCorporateCustomer(CreateRentalRequest createRentalRequest) {
		Car car = new Car();
		car.setId(createRentalRequest.getCarId());

		CorporateCustomer corporateCustomer = new CorporateCustomer();
		corporateCustomer.setId(createRentalRequest.getCustomerId());

		var result = BusinessRules.run(checkCarIsReturned(createRentalRequest.getCarId()),
				checkCorporateCustomerFindexPoint(
						this.corporateCustomerDao.getById(createRentalRequest.getCustomerId()),
						this.carDao.getById(createRentalRequest.getCarId())));
		if (result != null) {
			return result;
		}
		Rental rental = new Rental();
		rental.setRentDate(createRentalRequest.getRentDate());
		rental.setCar(car);
		rental.setCustomer(corporateCustomer);

		this.rentalDao.save(rental);
		return new SuccessResult(Messages.RENTALADD);
	}

	@Override
	public Result updateRentalForCorporateCustomer(UpdateRentalRequest updateRentalRequest) {
		Car car = new Car();
		car.setId(updateRentalRequest.getCarId());

		CorporateCustomer corporateCustomer = new CorporateCustomer();
		corporateCustomer.setId(updateRentalRequest.getCustomerId());

		var result = BusinessRules.run(checkCorporateCustomerFindexPoint(
				this.corporateCustomerDao.getById(updateRentalRequest.getCustomerId()),
				this.carDao.getById(updateRentalRequest.getCarId())));
		if (result != null) {
			return result;
		}
		Rental rental = this.rentalDao.getById(updateRentalRequest.getId());
		rental.setRentDate(updateRentalRequest.getRentDate());
		rental.setReturnDate(updateRentalRequest.getReturnDate());
		rental.setReturnStatus(updateRentalRequest.isRentStatus());
		rental.setCar(car);
		rental.setCustomer(corporateCustomer);

		this.rentalDao.save(rental);
		return new SuccessResult(Messages.RENTALUPDATE);
	}

	@Override
	public DataResult<List<Rental>> getAll() {
		return new SuccessDataResult<List<Rental>>(this.rentalDao.findAll(), Messages.RENTALADD);
	}

	private Result checkIndiviualCustomerFindexPoint(IndividualCustomer individualCustomer, Car car) {

		if (this.userFindexPointCheckService.checkIndividualCustomerFindexPoint(individualCustomer) <= car
				.getFindexPoint()) {
			return new ErrorResult(Messages.RENTALFINDEXPOINTERROR);
		}
		return new SuccessResult();
	}

	private Result checkCorporateCustomerFindexPoint(CorporateCustomer corporateCustomer, Car car) {

		if (this.userFindexPointCheckService.checkCorporateCustomerFindexPoint(corporateCustomer) <= car
				.getFindexPoint()) {

			return new ErrorResult(Messages.RENTALFINDEXPOINTERROR);
		}
		return new SuccessResult();
	}

	private Result checkCarIsReturned(int carId) {

		RentalDetailDto rentalDetailDto = this.rentalDao.getByCarIdWhereReturnDateIsNull(carId);
		if (rentalDetailDto != null) {
			return new ErrorResult(Messages.RENTALDATEERROR);
		}
		return new SuccessResult();
	}

}
