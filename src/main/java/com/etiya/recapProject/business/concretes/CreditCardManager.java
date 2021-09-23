package com.etiya.recapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.CreditCardService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.CreditCardDao;
import com.etiya.recapProject.entities.abstracts.Customer;
import com.etiya.recapProject.entities.concretes.CreditCard;
import com.etiya.recapProject.entities.requests.creditCardRequest.CreateCreditCardRequest;
import com.etiya.recapProject.entities.requests.creditCardRequest.DeleteCreditCardRequest;
import com.etiya.recapProject.entities.requests.creditCardRequest.UpdateCreditCardRequest;

@Service
public class CreditCardManager implements CreditCardService {

	private CreditCardDao creditCardDao;

	@Autowired
	public CreditCardManager(CreditCardDao creditCardDao) {
		super();
		this.creditCardDao = creditCardDao;
	}

	@Override
	public Result add(CreateCreditCardRequest createCreditCardRequest) {
		CreditCard creditCard = new CreditCard();

		creditCard.setCardDate(createCreditCardRequest.getCardDate());
		creditCard.setCardName(createCreditCardRequest.getCardName());
		creditCard.setCardNumber(createCreditCardRequest.getCardNumber());
		creditCard.setCvc(createCreditCardRequest.getCvc());

		Customer customer = new Customer();
		customer.setId(createCreditCardRequest.getCustomerId());

		this.creditCardDao.save(creditCard);

		return new SuccessResult(Messages.CREDITCARDADD);
	}

	@Override
	public Result update(UpdateCreditCardRequest updateCreditCardRequest) {
		Customer customer = new Customer();
		customer.setId(updateCreditCardRequest.getCustomerId());

		CreditCard creditCard = this.creditCardDao.getById(updateCreditCardRequest.getCardId());
		creditCard.setCardDate(updateCreditCardRequest.getCardDate());
		creditCard.setCardName(updateCreditCardRequest.getCardName());
		creditCard.setCardNumber(updateCreditCardRequest.getCardNumber());
		creditCard.setCvc(updateCreditCardRequest.getCvc());
		creditCard.setCustomer(customer);

		this.creditCardDao.save(creditCard);

		return new SuccessResult(Messages.CREDITCARDUPDATE);
	}

	@Override
	public Result delete(DeleteCreditCardRequest deleteCreditCardRequest) {
		CreditCard creditCard = this.creditCardDao.getById(deleteCreditCardRequest.getId());

		this.creditCardDao.delete(creditCard);
		
		return new SuccessResult(Messages.CREDITCARDELETE);
	}

	@Override
	public DataResult<List<CreditCard>> getAll() {
		this.creditCardDao.findAll();

		return new SuccessDataResult<List<CreditCard>>(Messages.CREDITCARDLIST);
	}

	@Override
	public DataResult<List<CreditCard>> getCreditCardByCustomer_Id(int customerId) {

		this.creditCardDao.getCreditCardByCustomer_Id(customerId);

		return new SuccessDataResult<List<CreditCard>>(Messages.CREDITCARDLIST);
	}

}
