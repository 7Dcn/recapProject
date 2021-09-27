package com.etiya.recapProject.business.concretes;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.CreditCardService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.business.BusinessRules;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.ErrorResult;
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
		
		var result = BusinessRules.run(checkCreditCardNumber(createCreditCardRequest.getCardNumber()),
				checkCreditCardCvc(createCreditCardRequest.getCvc()),
				checkCreditCardExpiryDate(createCreditCardRequest.getExpiryDate()));

		if (result != null) {
			return result;
		}
		
		CreditCard creditCard = new CreditCard();
		creditCard.setExpiryDate(createCreditCardRequest.getExpiryDate());
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
		creditCard.setExpiryDate(updateCreditCardRequest.getExpiryDate());
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

	private Result checkCreditCardNumber(String cardNumber) {
		String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" + "(?<mastercard>5[1-5][0-9]{14})|"
				+ "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" + "(?<amex>3[47][0-9]{13})|"
				+ "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" + "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(cardNumber);
		
		if(!matcher.matches()) {
			return new ErrorResult(Messages.CREDITCARDNUMBERERROR);
		}
		return new SuccessResult();
	}
	
	private Result checkCreditCardCvc(String cvc) {
		String regex = "^[0-9]{3, 4}$";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(cvc);
		
		if(!matcher.matches()) {
			return new ErrorResult(Messages.CREDITCARDCVCERROR);
		}
		return new SuccessResult();
	}

	private Result checkCreditCardExpiryDate(String expiryDate) {
		String regex = "^(0[1-9]|1[0-2])/?(([0-9]{4}|[0-9]{2})$)";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(expiryDate);

		if (!matcher.matches()) {
			return new ErrorResult(Messages.CREDITCARDDATEERROR);
		}
		return new SuccessResult();
	}
	
}
