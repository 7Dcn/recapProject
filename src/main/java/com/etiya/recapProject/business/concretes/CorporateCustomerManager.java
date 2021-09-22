package com.etiya.recapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.CorporateCustomerService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.business.BusinessRules;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.ErrorResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.CorporateCustomerDao;
import com.etiya.recapProject.dataAccess.abstracts.CustomerDao;
import com.etiya.recapProject.entities.concretes.CorporateCustomer;
import com.etiya.recapProject.entities.requests.corporateCustomerRequest.CreateCorporateCustomerRequest;
import com.etiya.recapProject.entities.requests.corporateCustomerRequest.DeleteCorporateCustomerRequest;
import com.etiya.recapProject.entities.requests.corporateCustomerRequest.UpdateCorporateCustomerRequest;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

	@Autowired
	private CorporateCustomerDao corporateCustomerDao;
	private CustomerDao customerDao;

	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, CustomerDao customerDao) {
		super();
		this.corporateCustomerDao = corporateCustomerDao;
		this.customerDao = customerDao;
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) {

		var result = BusinessRules.run(checkEmail(createCorporateCustomerRequest.getEmail()));
		if (result != null) {
			return result;
		}
		
		CorporateCustomer corporateCustomer = new CorporateCustomer();

		corporateCustomer.setCompanyName(createCorporateCustomerRequest.getCompanyName());
		corporateCustomer.setEmail(createCorporateCustomerRequest.getEmail());
		corporateCustomer.setPassword(createCorporateCustomerRequest.getPassword());
		corporateCustomer.setTaxNumber(createCorporateCustomerRequest.getTaxNumber());

		this.corporateCustomerDao.save(corporateCustomer);
		return new SuccessResult(Messages.CUSTOMERADD);
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {
		CorporateCustomer corporateCustomer = this.corporateCustomerDao
				.getByTaxNumber(updateCorporateCustomerRequest.getTaxNumber());
		corporateCustomer.setId(updateCorporateCustomerRequest.getId());

		corporateCustomer.setCompanyName(updateCorporateCustomerRequest.getCompanyName());
		corporateCustomer.setEmail(updateCorporateCustomerRequest.getEmail());
		corporateCustomer.setPassword(updateCorporateCustomerRequest.getPassword());
		corporateCustomer.setTaxNumber(updateCorporateCustomerRequest.getTaxNumber());

		this.corporateCustomerDao.save(corporateCustomer);
		return new SuccessResult(Messages.CUSTOMERUPDATE);
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) {
		CorporateCustomer corporateCustomer = new CorporateCustomer();
		corporateCustomer
				.setId(this.corporateCustomerDao.getByTaxNumber(deleteCorporateCustomerRequest.getTaxNumber()).getId());

		this.corporateCustomerDao.deleteById(corporateCustomer.getId());
		return new SuccessResult(Messages.CUSTOMERDELETE);
	}

	@Override
	public DataResult<List<CorporateCustomer>> getAll() {
		return new SuccessDataResult<List<CorporateCustomer>>(this.corporateCustomerDao.findAll(),
				Messages.CUSTOMERLIST);
	}

	public Result checkEmail(String email) {

		if (this.customerDao.existsCustomerByEmail(email)) {
			return new ErrorResult(Messages.EMAILERROR);
		}
		return new SuccessResult();
	}

}
