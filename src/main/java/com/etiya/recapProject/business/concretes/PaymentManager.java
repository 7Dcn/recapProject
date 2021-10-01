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
import com.etiya.recapProject.dataAccess.abstracts.CreditCardDao;
import com.etiya.recapProject.dataAccess.abstracts.PaymentDao;
import com.etiya.recapProject.entities.concretes.CreditCard;
import com.etiya.recapProject.entities.concretes.Payment;
import com.etiya.recapProject.entities.requests.paymentRequest.CreatePaymentRequest;

@Service
public class PaymentManager implements PaymentService {

	private PaymentDao paymentDao;
	private PosCheckService posCheckService;
	private CreditCardDao creditCardDao;
	
	@Autowired
	public PaymentManager(PaymentDao paymentDao, PosCheckService posCheckService,
			CreditCardDao creditCardDao) {
		super();
		this.paymentDao = paymentDao;
		this.posCheckService = posCheckService;
		this.creditCardDao = creditCardDao;
	}

	@Override
	public Result add(CreatePaymentRequest createPaymentRequest) {

		CreditCard creditCard = this.creditCardDao.getById(createPaymentRequest.getCreditCardId());

		var result = BusinessRules.run(checkCreditCardInformations(createPaymentRequest.getAmount(), creditCard.getCardNumber()));
		if (result != null) {
			return result;
		}

		Payment payment = new Payment();
		payment.setCreditCard(creditCard);
		payment.setAmount(createPaymentRequest.getAmount());
		
		this.paymentDao.save(payment);
		return new SuccessResult(Messages.PAYMENTADD);
	}

	@Override
	public DataResult<List<Payment>> getAll() {
		return new SuccessDataResult<List<Payment>>(this.paymentDao.findAll(), Messages.PAYMENTLIST);
	}

//	private Result cardInformationSave(boolean save) {
//		if (save == false) {
//			return new ErrorResult(Messages.PAYMENTCARDNOTSAVE);
//		}
//		return new SuccessResult(Messages.PAYMENTCARDSAVE);
//	}

	private Result checkCreditCardInformations(double amount, String cardNumber) {
		if (!this.posCheckService.checkCreditCardInformation(amount, cardNumber)) {
			return new ErrorResult(Messages.PAYMENTCARDFAIL);
		}
		return new SuccessResult();
	}

}
