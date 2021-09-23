package com.etiya.recapProject.business.abstracts;

import java.util.List;

import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.Rental;
import com.etiya.recapProject.entities.requests.rentalRequest.CreateRentalRequest;
import com.etiya.recapProject.entities.requests.rentalRequest.DeleteRentalRequest;
import com.etiya.recapProject.entities.requests.rentalRequest.UpdateRentalRequest;

public interface RentalService {
	
	Result addRentalForIndividualCustomer(CreateRentalRequest createRentalRequest);

	Result updateRentalForIndividualCustomer(UpdateRentalRequest updateRentalRequest);
	
	Result addRentalForCorporateCustomer(CreateRentalRequest createRentalRequest);
	
	Result updateRentalForCorporateCustomer(UpdateRentalRequest updateRentalRequest);
	
	Result deleteRental(DeleteRentalRequest deleteRentalRequest);
	
	DataResult<List<Rental>> getAll();
}
