package com.etiya.recapProject.business.abstracts;

import java.util.List;

import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.Payment;
import com.etiya.recapProject.entities.requests.Payment.CreatePaymentRequest;
import com.etiya.recapProject.entities.requests.Payment.DeletePaymentRequest;
import com.etiya.recapProject.entities.requests.Payment.UpdatePaymentRequest;

public interface PaymentService {
	Result add(CreatePaymentRequest createPaymentRequest);

	Result update(UpdatePaymentRequest updatePaymentRequest);

	Result delete(DeletePaymentRequest deletePaymentRequest);

	DataResult<List<Payment>> getAll();
}
