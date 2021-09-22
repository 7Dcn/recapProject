package com.etiya.recapProject.business.abstracts;

import java.util.List;

import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.CorporateCustomer;
import com.etiya.recapProject.entities.requests.corporateCustomerRequest.CreateCorporateCustomerRequest;
import com.etiya.recapProject.entities.requests.corporateCustomerRequest.DeleteCorporateCustomerRequest;
import com.etiya.recapProject.entities.requests.corporateCustomerRequest.UpdateCorporateCustomerRequest;

public interface CorporateCustomerService {

	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest);

	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest);

	Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest);

	DataResult<List<CorporateCustomer>> getAll();
}
