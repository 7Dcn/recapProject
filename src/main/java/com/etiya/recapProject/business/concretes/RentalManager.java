package com.etiya.recapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.RentalService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.business.BusinessRules;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.ErrorResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.RentalDao;
import com.etiya.recapProject.entities.concretes.Car;
import com.etiya.recapProject.entities.concretes.Customer;
import com.etiya.recapProject.entities.concretes.Rental;
import com.etiya.recapProject.entities.requests.CreateRentalRequest;

@Service
public class RentalManager implements RentalService {

	@Autowired
	private RentalDao rentalDao;

	public RentalManager(RentalDao rentalDao) {
		super();
		this.rentalDao = rentalDao;
	}

	@Override
	public Result add(CreateRentalRequest createRentalRequest) {
		Car car = new Car();
		car.setId(createRentalRequest.getCarId());

		Customer customer = new Customer();
		customer.setId(createRentalRequest.getCustomerId());

		Rental rental = new Rental();
		rental.setRentDate(createRentalRequest.getRentDate());
		rental.setReturnDate(createRentalRequest.getReturnDate());
		rental.setCar(car);
		rental.setCustomer(customer);
		rental.setReturnStatus(createRentalRequest.isRentStatus());

		var result = BusinessRules.run(checkCarIsReturned());

		if (result != null) {
			return result;
		}

		this.rentalDao.save(rental);
		return new SuccessResult(Messages.RENTALADD);

	}

	@Override
	public DataResult<List<Rental>> getAll() {
		return new SuccessDataResult<List<Rental>>(this.rentalDao.findAll(), Messages.RENTALADD);
	}

	private Result checkCarIsReturned() {
		if (this.rentalDao.existsByReturnStatusIsFalse()) {
			return new ErrorResult(Messages.RENTALDATEERROR);
		}
		return new SuccessResult();
	}

}
