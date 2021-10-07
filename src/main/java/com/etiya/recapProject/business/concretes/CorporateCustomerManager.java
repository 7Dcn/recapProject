package com.etiya.recapProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
import com.etiya.recapProject.entities.dtos.CorporateCustomerDto;
import com.etiya.recapProject.entities.requests.corporateCustomerRequest.CreateCorporateCustomerRequest;
import com.etiya.recapProject.entities.requests.corporateCustomerRequest.DeleteCorporateCustomerRequest;
import com.etiya.recapProject.entities.requests.corporateCustomerRequest.UpdateCorporateCustomerRequest;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

	private CorporateCustomerDao corporateCustomerDao;
	private CustomerDao customerDao;
	private ModelMapper modelMapper;

	@Autowired
	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, CustomerDao customerDao,
			ModelMapper modelMapper) {
		super();
		this.corporateCustomerDao = corporateCustomerDao;
		this.customerDao = customerDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) {

		var result = BusinessRules.run(checkEmailDuplication(createCorporateCustomerRequest.getEmail()));
		if (result != null) {
			return result;
		}

		CorporateCustomer corporateCustomer = modelMapper.map(createCorporateCustomerRequest, CorporateCustomer.class);
		this.corporateCustomerDao.save(corporateCustomer);

		return new SuccessResult(Messages.CUSTOMERADD);
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {
		CorporateCustomer corporateCustomer = modelMapper.map(updateCorporateCustomerRequest, CorporateCustomer.class);
		this.corporateCustomerDao.save(corporateCustomer);

		return new SuccessResult(Messages.CUSTOMERUPDATE);
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) {
		CorporateCustomer corporateCustomer = this.corporateCustomerDao.getById(deleteCorporateCustomerRequest.getId());

		this.corporateCustomerDao.delete(corporateCustomer);
		return new SuccessResult(Messages.CUSTOMERDELETE);
	}

	@Override
	public DataResult<List<CorporateCustomerDto>> getAll() {
		List<CorporateCustomer> corporateCustomers = this.corporateCustomerDao.findAll();
		List<CorporateCustomerDto> result = corporateCustomers.stream()
				.map(corporateCustomer -> modelMapper.map(corporateCustomer, CorporateCustomerDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CorporateCustomerDto>>(result, Messages.CUSTOMERLIST);
	}

	public Result checkEmailDuplication(String email) {

		if (this.customerDao.existsCustomerByEmail(email)) {
			return new ErrorResult(Messages.EMAILERROR);
		}
		return new SuccessResult();
	}

}
