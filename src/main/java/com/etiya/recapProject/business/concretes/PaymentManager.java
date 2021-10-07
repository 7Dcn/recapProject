package com.etiya.recapProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
import com.etiya.recapProject.entities.dtos.PaymentDto;
import com.etiya.recapProject.entities.requests.paymentRequest.CreatePaymentRequest;

@Service
public class PaymentManager implements PaymentService {

	private PaymentDao paymentDao;
	private PosCheckService posCheckService;
	private ModelMapper modelMapper;

	@Autowired
	public PaymentManager(PaymentDao paymentDao, PosCheckService posCheckService, ModelMapper modelMapper) {
		super();
		this.paymentDao = paymentDao;
		this.posCheckService = posCheckService;
		this.modelMapper = modelMapper;
	}

	@Override
	public Result add(CreatePaymentRequest createPaymentRequest) {

		var result = BusinessRules
				.run(checkCreditCardInformations(createPaymentRequest.getAmount(), createPaymentRequest.getCardNumber(),
						createPaymentRequest.getCvc(), createPaymentRequest.getExpiryDate()));
		if (result != null) {
			return result;
		}

		Payment payment = modelMapper.map(createPaymentRequest, Payment.class);

		this.paymentDao.save(payment);

		return new SuccessResult(Messages.PAYMENTADD);
	}

	@Override
	public DataResult<List<PaymentDto>> getAll() {
		List<Payment> payments = this.paymentDao.findAll();

		List<PaymentDto> result = payments.stream().map(payment -> modelMapper.map(payment, PaymentDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<PaymentDto>>(result, Messages.PAYMENTLIST);
	}

	private Result checkCreditCardInformations(double amount, String cardNumber, String cvc, String expiryDate) {
		if (!this.posCheckService.checkCreditCardInformation(amount, cardNumber, cvc, expiryDate)) {
			return new ErrorResult(Messages.PAYMENTCARDFAIL);
		}
		return new SuccessResult();
	}

}
