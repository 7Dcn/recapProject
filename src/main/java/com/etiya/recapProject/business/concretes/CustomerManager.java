package com.etiya.recapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.CustomerService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.CustomerDao;
import com.etiya.recapProject.entities.concretes.Customer;
import com.etiya.recapProject.entities.requests.CreateCustomerRequest;
import com.etiya.recapProject.entities.requests.DeleteCustomerRequest;

@Service
public class CustomerManager implements CustomerService {

	@Autowired
	private CustomerDao customerDao;

	public CustomerManager(CustomerDao customerDao) {
		super();
		this.customerDao = customerDao;
	}

	@Override
	public Result add(CreateCustomerRequest createCustomerRequest) {

		Customer customer = new Customer();
		customer.setCompanyName(createCustomerRequest.getCompanyName());
		customer.setEmail(createCustomerRequest.getEmail());
		customer.setPassword(createCustomerRequest.getPassword());

		this.customerDao.save(customer);
		return new SuccessResult(Messages.CUSTOMERADD);
	}

	@Override
	public Result update(Customer customer) {
		this.customerDao.save(customer);
		return new SuccessResult(Messages.CUSTOMERUPDATE);
	}

	@Override
	public Result delete(DeleteCustomerRequest deleteCustomerRequest) {
		Customer customer = new Customer();
		customer.setId(this.customerDao.getByCompanyName(deleteCustomerRequest.getCompanyName()).getId());

		this.customerDao.deleteById(customer.getId());
		return new SuccessResult(Messages.CUSTOMERDELETE);
	}

	@Override
	public DataResult<List<Customer>> getAll() {
		return new SuccessDataResult<List<Customer>>(this.customerDao.findAll(), Messages.CUSTOMERLIST);
	}

}
