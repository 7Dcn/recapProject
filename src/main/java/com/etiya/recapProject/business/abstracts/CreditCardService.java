package com.etiya.recapProject.business.abstracts;

import java.util.List;

import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.dtos.CreditCardDto;
import com.etiya.recapProject.entities.requests.creditCardRequest.CreateCreditCardRequest;
import com.etiya.recapProject.entities.requests.creditCardRequest.DeleteCreditCardRequest;
import com.etiya.recapProject.entities.requests.creditCardRequest.UpdateCreditCardRequest;

public interface CreditCardService {

	Result add(CreateCreditCardRequest createCreditCardRequest);

	Result update(UpdateCreditCardRequest updateCreditCardRequest);

	Result delete(DeleteCreditCardRequest deleteCreditCardRequest);

	DataResult<List<CreditCardDto>> getAll();

	DataResult<List<CreditCardDto>> getCreditCardByCustomer_Id(int customerId);
}
