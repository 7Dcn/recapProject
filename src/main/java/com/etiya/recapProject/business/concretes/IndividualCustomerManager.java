package com.etiya.recapProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
import com.etiya.recapProject.entities.dtos.IndividualCustomerDto;
import com.etiya.recapProject.entities.requests.individualCustomerRequest.CreateIndividualCustomerRequest;
import com.etiya.recapProject.entities.requests.individualCustomerRequest.DeleteIndividualCustomerRequest;
import com.etiya.recapProject.entities.requests.individualCustomerRequest.UpdateIndividualCustomerRequest;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

	private IndividualCustomerDao individualCustomerDao;
	private CustomerDao customerDao;
	private ModelMapper modelMapper;

	@Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, CustomerDao customerDao,
			ModelMapper modelMapper) {
		super();
		this.individualCustomerDao = individualCustomerDao;
		this.customerDao = customerDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {

		var result = BusinessRules.run(checkEmailDuplication(createIndividualCustomerRequest.getEmail()));
		if (result != null) {
			return result;
		}

		IndividualCustomer individualCustomer = modelMapper.map(createIndividualCustomerRequest,
				IndividualCustomer.class);

		this.individualCustomerDao.save(individualCustomer);
		return new SuccessResult(Messages.CUSTOMERADD);
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		IndividualCustomer individualCustomer = modelMapper.map(updateIndividualCustomerRequest,
				IndividualCustomer.class);

		this.individualCustomerDao.save(individualCustomer);
		return new SuccessResult(Messages.CUSTOMERUPDATE);
	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
		IndividualCustomer individualCustomer = this.individualCustomerDao
				.getById(deleteIndividualCustomerRequest.getId());

		this.individualCustomerDao.delete(individualCustomer);
		return new SuccessResult(Messages.CUSTOMERDELETE);
	}

	@Override
	public DataResult<List<IndividualCustomerDto>> getAll() {
		List<IndividualCustomer> individualCustomers = this.individualCustomerDao.findAll();

		List<IndividualCustomerDto> result = individualCustomers.stream()
				.map(individualCustomer -> modelMapper.map(individualCustomer, IndividualCustomerDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<IndividualCustomerDto>>(result, Messages.CUSTOMERLIST);
	}

	public Result checkEmailDuplication(String email) {

		if (this.customerDao.existsCustomerByEmail(email)) {
			return new ErrorResult(Messages.EMAILERROR);
		}
		return new SuccessResult();
	}

}
