package com.etiya.recapProject.business.concretes;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
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
import com.etiya.recapProject.dataAccess.abstracts.AdditionalServiceDao;
import com.etiya.recapProject.dataAccess.abstracts.CarDao;
import com.etiya.recapProject.dataAccess.abstracts.CarMaintenanceDao;
import com.etiya.recapProject.dataAccess.abstracts.CorporateCustomerDao;
import com.etiya.recapProject.dataAccess.abstracts.CreditCardDao;
import com.etiya.recapProject.dataAccess.abstracts.CustomerDao;
import com.etiya.recapProject.dataAccess.abstracts.IndividualCustomerDao;
import com.etiya.recapProject.dataAccess.abstracts.RentalDao;
import com.etiya.recapProject.entities.concretes.AdditionalService;
import com.etiya.recapProject.entities.concretes.Car;
import com.etiya.recapProject.entities.concretes.CarMaintenance;
import com.etiya.recapProject.entities.concretes.CorporateCustomer;
import com.etiya.recapProject.entities.concretes.CreditCard;
import com.etiya.recapProject.entities.concretes.IndividualCustomer;
import com.etiya.recapProject.entities.concretes.Rental;
import com.etiya.recapProject.entities.dtos.AdditionalServiceDto;
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
	private AdditionalServiceDao additionalServiceDao;
	private CreditCardDao creditCardDao;
	private CustomerDao customerDao;

	@Autowired
	public RentalManager(RentalDao rentalDao, CustomerFindexPointCheckService userFindexPointCheckService,
			IndividualCustomerDao individualCustomerDao, CarDao carDao, CorporateCustomerDao corporateCustomerDao,
			CarMaintenanceDao carMaintenanceDao, PaymentService paymentService,
			AdditionalServiceDao additionalServiceDao, CreditCardDao creditCardDao, CustomerDao customerDao) {
		super();
		this.rentalDao = rentalDao;
		this.userFindexPointCheckService = userFindexPointCheckService;
		this.individualCustomerDao = individualCustomerDao;
		this.carDao = carDao;
		this.corporateCustomerDao = corporateCustomerDao;
		this.carMaintenanceDao = carMaintenanceDao;
		this.paymentService = paymentService;
		this.additionalServiceDao = additionalServiceDao;
		this.creditCardDao = creditCardDao;
		this.customerDao = customerDao;
	}

	@Override
	public Result addRentalForIndividualCustomer(CreateRentalRequest createRentalRequest) {

		double amount = calculateTotalAmountofRental(createRentalRequest.getCarId(), createRentalRequest.getRentDate(),
				createRentalRequest.getReturnDate(), createRentalRequest.getDropOffLocation(),
				createRentalRequest.getPickUpLocation(), createRentalRequest.getAdditionalServiceDtos());

		var result = BusinessRules.run(checkCarIsReturned(createRentalRequest.getCarId()),
				checkIndiviualCustomerFindexPoint(
						this.individualCustomerDao.getById(createRentalRequest.getCustomerId()),
						this.carDao.getById(createRentalRequest.getCarId())),
				checkIfCarInMaintenance(createRentalRequest.getCarId()),
				isPaymentDone(createRentalRequest.getPaymentDto(), amount, createRentalRequest.getCustomerId()));

		if (result != null) {
			return result;
		}

		Car car = this.carDao.getById(createRentalRequest.getCarId());

		IndividualCustomer individualCustomer = this.individualCustomerDao.getById(createRentalRequest.getCustomerId());

		Rental rental = new Rental();
		rental.setRentDate(createRentalRequest.getRentDate());
		rental.setPickUpLocation(car.getCityName());
		rental.setDropOffLocation(createRentalRequest.getDropOffLocation());
		rental.setStartKilometer(car.getCurrentKilometer());
		rental.setCar(car);
		rental.setCustomer(individualCustomer);
		rental.setAdditionalServices(convertAdditionalServiceFromDto(createRentalRequest.getAdditionalServiceDtos()));
		rental.setAmount(amount);

		this.rentalDao.save(rental);
		
		car.setCityName(createRentalRequest.getDropOffLocation());
		this.carDao.save(car);
		
		return new SuccessResult(Messages.RENTALADD);

	}

	@Override
	public Result updateRentalForIndividualCustomer(UpdateRentalRequest updateRentalRequest) {

		Car car = this.carDao.getById(updateRentalRequest.getCarId());

		Rental rental = this.rentalDao.getById(updateRentalRequest.getId());

		// update sonrası amount miktarını hesaplarken ilk ödenen kısmı çıkaracak mıyız*
		double amount = calculateTotalAmountofRental(updateRentalRequest.getCarId(), rental.getRentDate(),
				updateRentalRequest.getReturnDate(), updateRentalRequest.getDropOffLocation(), car.getCityName(),
				updateRentalRequest.getAdditionalServiceDtos());

		var result = BusinessRules.run(
				checkIndiviualCustomerFindexPoint(
						this.individualCustomerDao.getById(updateRentalRequest.getCustomerId()),
						this.carDao.getById(updateRentalRequest.getCarId())),
				checkIfCarInMaintenance(updateRentalRequest.getCarId()),
				isPaymentDone(updateRentalRequest.getPaymentDto(), amount, updateRentalRequest.getCustomerId()));

		if (result != null) {
			return result;
		}

		IndividualCustomer individualCustomer = this.individualCustomerDao.getById(updateRentalRequest.getCustomerId());

		rental.setReturnDate(updateRentalRequest.getReturnDate());
		rental.setDropOffLocation(updateRentalRequest.getDropOffLocation());
		rental.setReturnStatus(updateRentalRequest.isRentStatus());
		rental.setEndKilometer(updateRentalRequest.getEndKilometer());
		rental.setAdditionalServices(convertAdditionalServiceFromDto(updateRentalRequest.getAdditionalServiceDtos()));
		rental.setCar(car);
		rental.setCustomer(individualCustomer);

		this.rentalDao.save(rental);

		car.setCityName(updateRentalRequest.getDropOffLocation());
		car.setCurrentKilometer(updateRentalRequest.getEndKilometer());
		this.carDao.save(car);
		
		return new SuccessResult(Messages.RENTALUPDATE);
	}

	@Override
	public Result addRentalForCorporateCustomer(CreateRentalRequest createRentalRequest) {
		
		double amount = calculateTotalAmountofRental(createRentalRequest.getCarId(), createRentalRequest.getRentDate(),
				createRentalRequest.getReturnDate(), createRentalRequest.getDropOffLocation(),
				createRentalRequest.getPickUpLocation(), createRentalRequest.getAdditionalServiceDtos());

		var result = BusinessRules.run(checkCarIsReturned(createRentalRequest.getCarId()),
				checkCorporateCustomerFindexPoint(
						this.corporateCustomerDao.getById(createRentalRequest.getCustomerId()),
						this.carDao.getById(createRentalRequest.getCarId())),
				checkIfCarInMaintenance(createRentalRequest.getCarId()),
				isPaymentDone(createRentalRequest.getPaymentDto(), amount, createRentalRequest.getCustomerId()));

		if (result != null) {
			return result;
		}
		
		Car car = this.carDao.getById(createRentalRequest.getCarId());

		CorporateCustomer corporateCustomer = this.corporateCustomerDao.getById(createRentalRequest.getCustomerId());

		Rental rental = new Rental();
		rental.setRentDate(createRentalRequest.getRentDate());
		rental.setPickUpLocation(car.getCityName());
		rental.setDropOffLocation(createRentalRequest.getDropOffLocation());
		rental.setStartKilometer(car.getCurrentKilometer());
		rental.setCar(car);
		rental.setCustomer(corporateCustomer);
		rental.setAdditionalServices(convertAdditionalServiceFromDto(createRentalRequest.getAdditionalServiceDtos()));
		rental.setAmount(amount);

		this.rentalDao.save(rental);
		
		car.setCityName(createRentalRequest.getDropOffLocation());
		this.carDao.save(car);
		
		return new SuccessResult(Messages.RENTALADD);
	}

	@Override
	public Result updateRentalForCorporateCustomer(UpdateRentalRequest updateRentalRequest) {

		Car car = this.carDao.getById(updateRentalRequest.getCarId());
		
		Rental rental = this.rentalDao.getById(updateRentalRequest.getId());

		CorporateCustomer corporateCustomer = this.corporateCustomerDao.getById(updateRentalRequest.getCustomerId());

		double amount = calculateTotalAmountofRental(updateRentalRequest.getCarId(), rental.getRentDate(),
				updateRentalRequest.getReturnDate(), updateRentalRequest.getDropOffLocation(), car.getCityName(),
				updateRentalRequest.getAdditionalServiceDtos());

		var result = BusinessRules.run(
				checkCorporateCustomerFindexPoint(
						this.corporateCustomerDao.getById(updateRentalRequest.getCustomerId()),
						this.carDao.getById(updateRentalRequest.getCarId())),
				checkIfCarInMaintenance(updateRentalRequest.getCarId()),
				isPaymentDone(updateRentalRequest.getPaymentDto(), amount, updateRentalRequest.getCustomerId()));

		if (result != null) {
			return result;
		}

		rental.setReturnDate(updateRentalRequest.getReturnDate());
		rental.setDropOffLocation(updateRentalRequest.getDropOffLocation());
		rental.setReturnStatus(updateRentalRequest.isRentStatus());
		rental.setEndKilometer(updateRentalRequest.getEndKilometer());
		rental.setAdditionalServices(convertAdditionalServiceFromDto(updateRentalRequest.getAdditionalServiceDtos()));
		rental.setCar(car);
		rental.setCustomer(corporateCustomer);

		this.rentalDao.save(rental);

		car.setCityName(updateRentalRequest.getDropOffLocation());
		car.setCurrentKilometer(updateRentalRequest.getEndKilometer());
		this.carDao.save(car);
		
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

	private Result isPaymentDone(PaymentDto paymentDto, double amount, int customerId) {
		CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest();
		createPaymentRequest.setCardNumber(paymentDto.getCardNumber());
		createPaymentRequest.setCvc(paymentDto.getCvc());
		createPaymentRequest.setExpiryDate(paymentDto.getExpiryDate());
		createPaymentRequest.setAmount(amount);

		if (!this.paymentService.add(createPaymentRequest).isSuccess()) {
			return new ErrorResult(Messages.PAYMENTCARDFAIL);
		}
		
		if(paymentDto.isSave()) {
			CreditCard creditCard = new CreditCard();
			creditCard.setCardNumber(paymentDto.getCardNumber());
			creditCard.setCvc(paymentDto.getCvc());
			creditCard.setExpiryDate(paymentDto.getExpiryDate());
			creditCard.setCustomer(this.customerDao.getById(customerId));
			creditCardDao.save(creditCard);
		}

		return new SuccessResult();
	}

	private double calculateTotalAmountofRental(int carId, Date rentDate, Date returnDate, String dropOffLocation,
			String pickUpLocation, List<AdditionalServiceDto> additionalServiceDtos) {

		Car car = this.carDao.getById(carId);

		long totalRentDay = (ChronoUnit.DAYS.between((rentDate.toInstant()), (returnDate.toInstant())));

		double totalAmount = totalRentDay * car.getDailyPrice();

		List<AdditionalService> additionalServices = new ArrayList<AdditionalService>();
		for (AdditionalServiceDto additionalServiceDtoItem : additionalServiceDtos) {
			additionalServices.add(this.additionalServiceDao.findById(additionalServiceDtoItem.getId()).get());
		}

		if (!dropOffLocation.equals(pickUpLocation)) {
			totalAmount += 500;

		}

		for (AdditionalService additionalServiceItem : additionalServices) {
			totalAmount += additionalServiceItem.getDailyPrice() * totalRentDay;
		}

		return totalAmount;
	}
	
	private List<AdditionalService> convertAdditionalServiceFromDto(List<AdditionalServiceDto> additionalServiceDtos){
		List<AdditionalService> additionalServices = new ArrayList<AdditionalService>();
		
		for (AdditionalServiceDto additionalServiceDtoItem : additionalServiceDtos) {
			additionalServices.add(this.additionalServiceDao.findById(additionalServiceDtoItem.getId()).get());
		}
		
		return additionalServices;
	}

}
