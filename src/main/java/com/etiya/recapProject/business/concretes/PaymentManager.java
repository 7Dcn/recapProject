package com.etiya.recapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.PaymentService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.ErrorResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.PaymentDao;
import com.etiya.recapProject.entities.concretes.CreditCard;
import com.etiya.recapProject.entities.concretes.Payment;
import com.etiya.recapProject.entities.concretes.Rental;
import com.etiya.recapProject.entities.requests.Payment.CreatePaymentRequest;
import com.etiya.recapProject.entities.requests.Payment.DeletePaymentRequest;
import com.etiya.recapProject.entities.requests.Payment.UpdatePaymentRequest;

@Service
public class PaymentManager implements PaymentService {

	private PaymentDao paymentDao;

	@Autowired
	public PaymentManager(PaymentDao paymentDao) {
		super();
		this.paymentDao = paymentDao;
	}

	@Override
	public Result add(CreatePaymentRequest createPaymentRequest) {
		Rental rental = new Rental();
		rental.setId(createPaymentRequest.getRentalId());

		CreditCard creditCard = new CreditCard();
		creditCard.setId(createPaymentRequest.getCreditCardId());

		Payment payment = new Payment();
		payment.setCreditCard(creditCard);
		payment.setRental(rental);
		
		this.paymentDao.save(payment);
		return new SuccessResult(Messages.PAYMENTADD);
	}

	@Override
	public Result update(UpdatePaymentRequest updatePaymentRequest) {
		Rental rental = new Rental();
		rental.setId(updatePaymentRequest.getRentalId());

		CreditCard creditCard = new CreditCard();
		creditCard.setId(updatePaymentRequest.getCreditCardId());

		Payment payment = this.paymentDao.getById(updatePaymentRequest.getId());
		payment.setCreditCard(creditCard);
		payment.setRental(rental);

		this.paymentDao.save(payment);
		return new SuccessResult(Messages.PAYMENTUPDATE);
	}

	@Override
	public Result delete(DeletePaymentRequest deletePaymentRequest) {
		Payment payment = this.paymentDao.getById(deletePaymentRequest.getId());

		this.paymentDao.delete(payment);
		return new SuccessResult(Messages.PAYMENTDELETE);
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

}
