package com.etiya.recapProject.business.concretes;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.AdditionalServiceService;
import com.etiya.recapProject.business.abstracts.CreditCardService;
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
import com.etiya.recapProject.entities.concretes.IndividualCustomer;
import com.etiya.recapProject.entities.concretes.Rental;
import com.etiya.recapProject.entities.dtos.AdditionalServiceIForRentalDto;
import com.etiya.recapProject.entities.dtos.PaymentDtoForRental;
import com.etiya.recapProject.entities.dtos.RentalDetailDto;
import com.etiya.recapProject.entities.dtos.RentalDto;
import com.etiya.recapProject.entities.requests.creditCardRequest.CreateCreditCardRequest;
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
	private CreditCardService creditCardService;
	private ModelMapper modelMapper;
	private AdditionalServiceService additionalServiceService;

	@Autowired
	public RentalManager(RentalDao rentalDao, CustomerFindexPointCheckService userFindexPointCheckService,
			IndividualCustomerDao individualCustomerDao, CarDao carDao, CorporateCustomerDao corporateCustomerDao,
			CarMaintenanceDao carMaintenanceDao, PaymentService paymentService,
			AdditionalServiceDao additionalServiceDao, CreditCardDao creditCardDao, CustomerDao customerDao,
			CreditCardService creditCardService, ModelMapper modelMapper, AdditionalServiceService additionalServiceService) {
		super();
		this.rentalDao = rentalDao;
		this.userFindexPointCheckService = userFindexPointCheckService;
		this.individualCustomerDao = individualCustomerDao;
		this.carDao = carDao;
		this.corporateCustomerDao = corporateCustomerDao;
		this.carMaintenanceDao = carMaintenanceDao;
		this.paymentService = paymentService;
		this.additionalServiceDao = additionalServiceDao;
		this.creditCardService = creditCardService;
		this.modelMapper = modelMapper;
		this.additionalServiceService = additionalServiceService;
	}

	@Override
	public Result addRentalForIndividualCustomer(CreateRentalRequest createRentalRequest) {

		double amount = calculateTotalAmountofRental(createRentalRequest.getCarId(), createRentalRequest.getRentDate(),
				createRentalRequest.getReturnDate(), createRentalRequest.getDropOffLocation(),
				this.carDao.getById(createRentalRequest.getCarId()).getCityName(),
				createRentalRequest.getAdditionalServiceDtos());

		var result = BusinessRules.run(
				checkCarIsReturned(createRentalRequest.getCarId()),
				checkIndiviualCustomerFindexPoint(
						this.individualCustomerDao.getById(createRentalRequest.getCustomerId()),
						this.carDao.getById(createRentalRequest.getCarId())),
				checkIfCarInMaintenance(createRentalRequest.getCarId()),
				isPaymentDone(createRentalRequest.getPaymentDto(), amount, createRentalRequest.getCustomerId()));

		if (result != null) {
			return result;
		}
		Car car = this.carDao.getById(createRentalRequest.getCarId());
		Rental rental = modelMapper.map(createRentalRequest, Rental.class);
		rental.setReturnDate(null);
		rental.setPickUpLocation(car.getCityName());
		rental.setStartKilometer(car.getCurrentKilometer());
		rental.setAdditionalServices(convertAdditionalServiceFromDto(createRentalRequest.getAdditionalServiceDtos()));
		rental.setAmount(amount);
		this.rentalDao.save(rental);

		car.setCityName(createRentalRequest.getDropOffLocation());
		this.carDao.save(car);

		return new SuccessResult(Messages.RENTALADD);

	}

	@Override
	public Result updateRentalForIndividualCustomer(UpdateRentalRequest updateRentalRequest) {
		// update sonrası amount miktarını hesaplarken ilk ödenen kısmı çıkaracak mıyız*
		double amount = calculateTotalAmountofRental(updateRentalRequest.getCarId(),
				this.rentalDao.getById(updateRentalRequest.getId()).getRentDate(), updateRentalRequest.getReturnDate(),
				updateRentalRequest.getDropOffLocation(),
				this.carDao.getById(updateRentalRequest.getCarId()).getCityName(),
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

		Car car = this.carDao.getById(updateRentalRequest.getCarId());
		Rental rental = modelMapper.map(updateRentalRequest, Rental.class);
		rental.setAdditionalServices(convertAdditionalServiceFromDto(updateRentalRequest.getAdditionalServiceDtos()));
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
				this.carDao.getById(createRentalRequest.getCarId()).getCityName(),
				createRentalRequest.getAdditionalServiceDtos());

		var result = BusinessRules.run(
				checkCarIsReturned(createRentalRequest.getCarId()),
				checkCorporateCustomerFindexPoint(
						this.corporateCustomerDao.getById(createRentalRequest.getCustomerId()),
						this.carDao.getById(createRentalRequest.getCarId())),
				checkIfCarInMaintenance(createRentalRequest.getCarId()),
				isPaymentDone(createRentalRequest.getPaymentDto(), amount, createRentalRequest.getCustomerId()));

		if (result != null) {
			return result;
		}

		Car car = this.carDao.getById(createRentalRequest.getCarId());

		Rental rental = modelMapper.map(createRentalRequest, Rental.class);
		rental.setReturnDate(null);
		rental.setPickUpLocation(car.getCityName());
		rental.setStartKilometer(car.getCurrentKilometer());
		rental.setAdditionalServices(convertAdditionalServiceFromDto(createRentalRequest.getAdditionalServiceDtos()));
		rental.setAmount(amount);
		this.rentalDao.save(rental);

		car.setCityName(createRentalRequest.getDropOffLocation());
		this.carDao.save(car);

		return new SuccessResult(Messages.RENTALADD);
	}

	@Override
	public Result updateRentalForCorporateCustomer(UpdateRentalRequest updateRentalRequest) {

		double amount = calculateTotalAmountofRental(updateRentalRequest.getCarId(),
				this.rentalDao.getById(updateRentalRequest.getId()).getRentDate(), updateRentalRequest.getReturnDate(),
				updateRentalRequest.getDropOffLocation(),
				this.carDao.getById(updateRentalRequest.getCarId()).getCityName(),
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

		Rental rental = modelMapper.map(updateRentalRequest, Rental.class);
		
		Car car = this.carDao.getById(updateRentalRequest.getCarId());

		rental.setAdditionalServices(convertAdditionalServiceFromDto(updateRentalRequest.getAdditionalServiceDtos()));
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
	public DataResult<List<RentalDto>> getAll() {
		List<Rental> rentals = this.rentalDao.findAll();
		
		List<RentalDto> result = new ArrayList<RentalDto>();
		
		for (Rental rentalItem : rentals) {
			RentalDto rentalDto = modelMapper.map(rentalItem, RentalDto.class);
			rentalDto.setAdditionalServiceDtos(this.additionalServiceService.findByRentals_Id(rentalItem.getId()).getData());
			result.add(rentalDto);
		}		
		
		return new SuccessDataResult<List<RentalDto>>(result, Messages.RENTALLIST);
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

	private Result isPaymentDone(PaymentDtoForRental paymentDto, double amount, int customerId) {
		CreatePaymentRequest createPaymentRequest = new CreatePaymentRequest();
		createPaymentRequest.setCardNumber(paymentDto.getCardNumber());
		createPaymentRequest.setCvc(paymentDto.getCvc());
		createPaymentRequest.setExpiryDate(paymentDto.getExpiryDate());
		createPaymentRequest.setAmount(amount);

		if (!this.paymentService.add(createPaymentRequest).isSuccess()) {
			return new ErrorResult(Messages.PAYMENTCARDFAIL);
		}

		if (paymentDto.isSaveCreditCard()) {
			CreateCreditCardRequest createCreditCardRequest = new CreateCreditCardRequest();
			createCreditCardRequest.setCardNumber(paymentDto.getCardNumber());
			createCreditCardRequest.setCvc(paymentDto.getCvc());
			createCreditCardRequest.setExpiryDate(paymentDto.getExpiryDate());
			createCreditCardRequest.setCustomerId(customerId);
			this.creditCardService.add(createCreditCardRequest);
		}

		return new SuccessResult();
	}

	private double calculateTotalAmountofRental(int carId, Date rentDate, Date returnDate, String dropOffLocation,
			String pickUpLocation, List<AdditionalServiceIForRentalDto> AdditionalServiceIdDtos) {

		Car car = this.carDao.getById(carId);

		long totalRentDay = (ChronoUnit.DAYS.between((rentDate.toInstant()), (returnDate.toInstant())));

		double totalAmount = totalRentDay * car.getDailyPrice();

		List<AdditionalService> additionalServices = new ArrayList<AdditionalService>();
		for (AdditionalServiceIForRentalDto additionalServiceDtoItem : AdditionalServiceIdDtos) {
			additionalServices.add(this.additionalServiceDao.findById(additionalServiceDtoItem.getId()).get());
		}

		for (AdditionalService additionalServiceItem : additionalServices) {
			totalAmount += additionalServiceItem.getDailyPrice() * totalRentDay;
		}

		if (!dropOffLocation.equals(pickUpLocation)) {
			totalAmount += 500;

		}

		return totalAmount;
	}

	private List<AdditionalService> convertAdditionalServiceFromDto(
			List<AdditionalServiceIForRentalDto> additionalServiceDtos) {
		List<AdditionalService> additionalServices = new ArrayList<AdditionalService>();

		for (AdditionalServiceIForRentalDto additionalServiceDtoItem : additionalServiceDtos) {
			additionalServices.add(this.additionalServiceDao.findById(additionalServiceDtoItem.getId()).get());
		}

		return additionalServices;
	}

}
