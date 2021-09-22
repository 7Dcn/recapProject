package com.etiya.recapProject.business.abstracts;

import java.util.List;

import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.IndividualCustomer;
import com.etiya.recapProject.entities.requests.individualCustomerRequest.CreateIndividualCustomerRequest;
import com.etiya.recapProject.entities.requests.individualCustomerRequest.DeleteIndividualCustomerRequest;
import com.etiya.recapProject.entities.requests.individualCustomerRequest.UpdateIndividualCustomerRequest;

public interface IndividualCustomerService {
	Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest);

	Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest);

	Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest);

	DataResult<List<IndividualCustomer>> getAll();
}
