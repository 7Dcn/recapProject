package com.etiya.recapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.IndividualCustomerService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.business.BusinessRules;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.ErrorResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.CustomerDao;
import com.etiya.recapProject.dataAccess.abstracts.IndividualCustomerDao;
import com.etiya.recapProject.entities.concretes.IndividualCustomer;
import com.etiya.recapProject.entities.requests.individualCustomerRequest.CreateIndividualCustomerRequest;
import com.etiya.recapProject.entities.requests.individualCustomerRequest.DeleteIndividualCustomerRequest;
import com.etiya.recapProject.entities.requests.individualCustomerRequest.UpdateIndividualCustomerRequest;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

	private IndividualCustomerDao individualCustomerDao;
	private CustomerDao customerDao;

	@Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, CustomerDao customerDao) {
		super();
		this.individualCustomerDao = individualCustomerDao;
		this.customerDao = customerDao;
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {

		var result = BusinessRules.run(checkEmailDuplication(createIndividualCustomerRequest.getEmail()));
		if (result != null) {
			return result;
		}
		
		IndividualCustomer individualCustomer = new IndividualCustomer();
		individualCustomer.setEmail(createIndividualCustomerRequest.getEmail());
		individualCustomer.setPassword(createIndividualCustomerRequest.getPassword());
		individualCustomer.setFirstName(createIndividualCustomerRequest.getFirstName());
		individualCustomer.setLastName(createIndividualCustomerRequest.getLastName());
		individualCustomer.setIdentityNumber(createIndividualCustomerRequest.getIdentityNumber());

		this.individualCustomerDao.save(individualCustomer);
		return new SuccessResult(Messages.CUSTOMERADD);
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		IndividualCustomer individualCustomer = this.individualCustomerDao.getById(updateIndividualCustomerRequest.getId());
		individualCustomer.setEmail(updateIndividualCustomerRequest.getEmail());
		individualCustomer.setPassword(updateIndividualCustomerRequest.getPassword());
		individualCustomer.setFirstName(updateIndividualCustomerRequest.getFirstName());
		individualCustomer.setLastName(updateIndividualCustomerRequest.getLastName());
		individualCustomer.setIdentityNumber(updateIndividualCustomerRequest.getIdentityNumber());
		
		this.individualCustomerDao.save(individualCustomer);
		return new SuccessResult(Messages.CUSTOMERUPDATE);
	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
		IndividualCustomer individualCustomer = this.individualCustomerDao.getById(deleteIndividualCustomerRequest.getId());

		this.individualCustomerDao.delete(individualCustomer);
		return new SuccessResult(Messages.CUSTOMERDELETE);
	}

	@Override
	public DataResult<List<IndividualCustomer>> getAll() {
		return new SuccessDataResult<List<IndividualCustomer>>(this.individualCustomerDao.findAll(), Messages.CUSTOMERLIST);
	}
	
	public Result checkEmailDuplication(String email) {

		if (this.customerDao.existsCustomerByEmail(email)) {
			return new ErrorResult(Messages.EMAILERROR);
		}
		return new SuccessResult();
	}

}
