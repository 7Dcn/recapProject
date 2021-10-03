package com.etiya.recapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.PaymentService;
import com.etiya.recapProject.business.abstracts.PosCheckService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.business.BusinessRules;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.ErrorResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.PaymentDao;
import com.etiya.recapProject.entities.concretes.Payment;
import com.etiya.recapProject.entities.requests.paymentRequest.CreatePaymentRequest;

@Service
public class PaymentManager implements PaymentService {

	private PaymentDao paymentDao;
	private PosCheckService posCheckService;

	@Autowired
	public PaymentManager(PaymentDao paymentDao, PosCheckService posCheckService) {
		super();
		this.paymentDao = paymentDao;
		this.posCheckService = posCheckService;
	}

	@Override
	public Result add(CreatePaymentRequest createPaymentRequest) {

		var result = BusinessRules
				.run(checkCreditCardInformations(createPaymentRequest.getAmount(), createPaymentRequest.getCardNumber(),
						createPaymentRequest.getCvc(), createPaymentRequest.getExpiryDate()));
		if (result != null) {
			return result;
		}

		Payment payment = new Payment();
		payment.setAmount(createPaymentRequest.getAmount());
		
		this.paymentDao.save(payment);

		return new SuccessResult(Messages.PAYMENTADD);
	}

	@Override
	public DataResult<List<Payment>> getAll() {
		return new SuccessDataResult<List<Payment>>(this.paymentDao.findAll(), Messages.PAYMENTLIST);
	}

	private Result checkCreditCardInformations(double amount, String cardNumber, String cvc, String expiryDate) {
		if (!this.posCheckService.checkCreditCardInformation(amount, cardNumber, cvc, expiryDate)) {
			return new ErrorResult(Messages.PAYMENTCARDFAIL);
		}
		return new SuccessResult();
	}

}
