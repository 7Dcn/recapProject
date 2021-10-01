package com.etiya.recapProject.business.abstracts;

import java.util.List;

import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.Payment;
import com.etiya.recapProject.entities.requests.paymentRequest.CreatePaymentRequest;

public interface PaymentService {
	Result add(CreatePaymentRequest createPaymentRequest);

	DataResult<List<Payment>> getAll();
}
