package com.etiya.recapProject.business.concretes;

import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.CustomerFindexPointCheckService;
import com.etiya.recapProject.business.abstracts.PaymentService;
import com.etiya.recapProject.business.abstracts.RentalService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.business.BusinessRules;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.ErrorResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.CarDao;
import com.etiya.recapProject.dataAccess.abstracts.CarMaintenanceDao;
import com.etiya.recapProject.dataAccess.abstracts.CorporateCustomerDao;
import com.etiya.recapProject.dataAccess.abstracts.IndividualCustomerDao;
import com.etiya.recapProject.dataAccess.abstracts.RentalDao;
import com.etiya.recapProject.entities.concretes.Car;
import com.etiya.recapProject.entities.concretes.CarMaintenance;
import com.etiya.recapProject.entities.concretes.CorporateCustomer;
import com.etiya.recapProject.entities.concretes.IndividualCustomer;
import com.etiya.recapProject.entities.concretes.Rental;
import com.etiya.recapProject.entities.dtos.PaymentDto;
import com.etiya.recapProject.entities.dtos.RentalDetailDto;
import com.etiya.recapProject.entities.requests.paymentRequest.CreatePaymentRequest;
import com.etiya.recapProject.entities.requests.rentalRequest.CreateRentalRequest;
import com.etiya.recapProject.entities.requests.rentalRequest.DeleteRentalRequest;
import com.etiya.recapProject.entities.requests.rentalRequest.UpdateRentalRequest;

@Service
public class RentalManager implements RentalService {

	private RentalDao rentalDao;
	private CustomerFindexPointCheckService userFindexPointCheckService;
	private IndividualCustomerDao individualCustomerDao;
	private CorporateCustomerDao corporateCustomerDao;
	private CarDao carDao;
	private CarMaintenanceDao carMaintenanceDao;
	private PaymentService paymentService;

	@Autowired
	public RentalManager(RentalDao rentalDao, CustomerFindexPointCheckService userFindexPointCheckService,
			IndividualCustomerDao individualCustomerDao, CarDao carDao, CorporateCustomerDao corporateCustomerDao,
			CarMaintenanceDao carMaintenanceDao, PaymentService paymentService) {
		super();
		this.rentalDao = rentalDao;
		this.userFindexPointCheckService = userFindexPointCheckService;
		this.individualCustomerDao = individualCustomerDao;
		this.carDao = carDao;
		this.corporateCustomerDao = corporateCustomerDao;
		this.carMaintenanceDao = carMaintenanceDao;
		this.paymentService = paymentService;
	}

	@Override
	public Result addRentalForIndividualCustomer(CreateRentalRequest createRentalRequest) {

		Car car = this.carDao.getById(createRentalRequest.getCarId());

		IndividualCustomer individualCustomer = new IndividualCustomer();
		individualCustomer.setId(createRentalRequest.getCustomerId());
		
		Rental rental = new Rental();
		rental.setRentDate(createRentalRequest.getRentDate());
		rental.setPickUpLocation(car.getCityName());
		rental.setDropOffLocation(createRentalRequest.getDropOffLocation());
		rental.setStartKilometer(createRentalRequest.getStartKilometer());
		rental.setDailyPrice(car.getDailyPrice());
		rental.setCar(car);
		rental.setCustomer(individualCustomer);

		long totalRentDay = (ChronoUnit.DAYS.between((createRentalRequest.getRentDate().toInstant()),
				(createRentalRequest.getReturnDate().toInstant())));
		double totalAmount = totalRentDay * car.getDailyPrice();
		rental.setAmount(totalAmount);
		
		
		
		var result = BusinessRules.run(checkCarIsReturned(createRentalRequest.getCarId()),
				checkIndiviualCustomerFindexPoint(
						this.individualCustomerDao.getById(createRentalRequest.getCustomerId()),
						this.carDao.getById(createRentalRequest.getCarId())),
				checkIfCarInMaintenance(createRentalRequest.getCarId()),
				isPaymentDone(createRentalRequest.getPaymentDto(),totalAmount));

		if (result != null) {
			return result;
		}
		
		this.rentalDao.save(rental);
		return new SuccessResult(Messages.RENTALADD);

	}

	@Override
	public Result updateRentalForIndividualCustomer(UpdateRentalRequest updateRentalRequest) {

		Car car = this.carDao.getById(updateRentalRequest.getCarId());
		car.setCityName(updateRentalRequest.getDropOffLocation());
		car.setCurrentKilometer(updateRentalRequest.getEndKilometer());
		this.carDao.save(car);

		IndividualCustomer individualCustomer = new IndividualCustomer();
		individualCustomer.setId(updateRentalRequest.getCustomerId());

		var result = BusinessRules.run(
				checkIndiviualCustomerFindexPoint(
						this.individualCustomerDao.getById(updateRentalRequest.getCustomerId()),
						this.carDao.getById(updateRentalRequest.getCarId())),
				checkIfCarInMaintenance(updateRentalRequest.getCarId()));
		if (result != null) {
			return result;
		}

		Rental rental = this.rentalDao.getById(updateRentalRequest.getId());
		rental.setRentDate(updateRentalRequest.getRentDate());
		rental.setReturnDate(updateRentalRequest.getReturnDate());
		rental.setPickUpLocation(updateRentalRequest.getPickUpLocation());
		rental.setDropOffLocation(updateRentalRequest.getDropOffLocation());
		rental.setReturnStatus(updateRentalRequest.isRentStatus());
		rental.setStartKilometer(updateRentalRequest.getStartKilometer());
		rental.setEndKilometer(updateRentalRequest.getEndKilometer());
		rental.setDailyPrice(car.getDailyPrice());
		rental.setCar(car);
		rental.setCustomer(individualCustomer);
		
		long totalRentDay = (ChronoUnit.DAYS.between((updateRentalRequest.getRentDate().toInstant()),
				(updateRentalRequest.getReturnDate().toInstant())));
		rental.setAmount(totalRentDay * car.getDailyPrice());

		this.rentalDao.save(rental);
		return new SuccessResult(Messages.RENTALUPDATE);
	}

	@Override
	public Result addRentalForCorporateCustomer(CreateRentalRequest createRentalRequest) {

		Car car = this.carDao.getById(createRentalRequest.getCarId());

		CorporateCustomer corporateCustomer = new CorporateCustomer();
		corporateCustomer.setId(createRentalRequest.getCustomerId());

		var result = BusinessRules.run(checkCarIsReturned(createRentalRequest.getCarId()),
				checkCorporateCustomerFindexPoint(
						this.corporateCustomerDao.getById(createRentalRequest.getCustomerId()),
						this.carDao.getById(createRentalRequest.getCarId())),
				checkIfCarInMaintenance(createRentalRequest.getCarId()));
		if (result != null) {
			return result;
		}
		Rental rental = new Rental();
		rental.setRentDate(createRentalRequest.getRentDate());
		rental.setPickUpLocation(car.getCityName());
		rental.setDropOffLocation(createRentalRequest.getDropOffLocation());
		rental.setStartKilometer(car.getCurrentKilometer());
		rental.setDailyPrice(car.getDailyPrice());
		rental.setCar(car);
		rental.setCustomer(corporateCustomer);
		
		long totalRentDay = (ChronoUnit.DAYS.between((createRentalRequest.getRentDate().toInstant()),
				(createRentalRequest.getReturnDate().toInstant())));
		rental.setAmount(totalRentDay * car.getDailyPrice());

		this.rentalDao.save(rental);
		return new SuccessResult(Messages.RENTALADD);
	}

	@Override
	public Result updateRentalForCorporateCustomer(UpdateRentalRequest updateRentalRequest) {

		Car car = this.carDao.getById(updateRentalRequest.getCarId());
		car.setCityName(updateRentalRequest.getDropOffLocation());
		car.setCurrentKilometer(updateRentalRequest.getEndKilometer());
		this.carDao.save(car);

		CorporateCustomer corporateCustomer = new CorporateCustomer();
		corporateCustomer.setId(updateRentalRequest.getCustomerId());

		var result = BusinessRules.run(
				checkCorporateCustomerFindexPoint(
						this.corporateCustomerDao.getById(updateRentalRequest.getCustomerId()),
						this.carDao.getById(updateRentalRequest.getCarId())),
				checkIfCarInMaintenance(updateRentalRequest.getCarId()));

		if (result != null) {
			return result;
		}

		Rental rental = this.rentalDao.getById(updateRentalRequest.getId());
		rental.setRentDate(updateRentalRequest.getRentDate());
		rental.setReturnDate(updateRentalRequest.getReturnDate());
		rental.setPickUpLocation(updateRentalRequest.getPickUpLocation());
		rental.setDropOffLocation(updateRentalRequest.getDropOffLocation());
		rental.setReturnStatus(updateRentalRequest.isRentStatus());
		rental.setStartKilometer(updateRentalRequest.getStartKilometer());
		rental.setEndKilometer(updateRentalRequest.getEndKilometer());
		rental.setCar(car);
		rental.setCustomer(corporateCustomer);

		long totalRentDay = (ChronoUnit.DAYS.between((updateRentalRequest.getRentDate().toInstant()),
				(updateRentalRequest.getReturnDate().toInstant())));
		rental.setAmount(totalRentDay * car.getDailyPrice());
		
		this.rentalDao.save(rental);
		return new SuccessResult(Messages.RENTALUPDATE);
	}

	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) {
		Rental rental = this.rentalDao.getById(deleteRentalRequest.getId());

		this.rentalDao.delete(rental);
		return new SuccessResult(Messages.RENTALDELETE);
	}

	@Override
	public DataResult<List<Rental>> getAll() {
		return new SuccessDataResult<List<Rental>>(this.rentalDao.findAll(), Messages.RENTALLIST);
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

	private Result checkIfCarInMaintenance(int carId) {
		if (this.carMaintenanceDao.getByCar_Id(carId).size() != 0) {
			CarMaintenance carMaintenance = this.carMaintenanceDao.getByCar_Id(carId)
					.get(this.carMaintenanceDao.getByCar_Id(carId).size() - 1);

			if (carMaintenance.getReturnDate() == null) {
				return new ErrorResult(Messages.RENTALMAINTENANCEERROR);
			}
		}
		return new SuccessResult();
	}
	
	private Result isPaymentDone(PaymentDto paymentDto, double amount) {
		CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest();
		createPaymentRequest.setCreditCardId(paymentDto.getCreditCardId());
		createPaymentRequest.setAmount(amount);
		
		if(!this.paymentService.add(createPaymentRequest).isSuccess()) {
			return new ErrorResult(Messages.PAYMENTCARDFAIL);
		}
		
		return new SuccessResult();
	}

}
