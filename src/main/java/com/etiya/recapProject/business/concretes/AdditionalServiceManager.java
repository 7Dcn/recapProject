package com.etiya.recapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.AdditionalServiceService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.business.BusinessRules;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.ErrorResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.AdditionalServiceDao;
import com.etiya.recapProject.entities.concretes.AdditionalService;
import com.etiya.recapProject.entities.requests.additionalServiceRequest.CreateAdditionalServiceRequest;
import com.etiya.recapProject.entities.requests.additionalServiceRequest.DeleteAdditionalServiceRequest;
import com.etiya.recapProject.entities.requests.additionalServiceRequest.UpdateAdditionalServiceRequest;

@Service
public class AdditionalServiceManager implements AdditionalServiceService {

	private AdditionalServiceDao additionalServiceDao;
	
	@Autowired
	public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao) {
		super();
		this.additionalServiceDao = additionalServiceDao;
	}

	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) {
		var result = BusinessRules.run(checkAdditionalServiceNameDuplication(createAdditionalServiceRequest.getName()));
		if (result != null) {
			return result;
		}
		
		AdditionalService additionalService = new AdditionalService();
		additionalService.setName(createAdditionalServiceRequest.getName());
		additionalService.setDescription(createAdditionalServiceRequest.getDescription());
		additionalService.setDailyPrice(createAdditionalServiceRequest.getDailyPrice());
		
		this.additionalServiceDao.save(additionalService);
		return new SuccessResult(Messages.ADDITIONALSERVICEADD);
	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
		var result = BusinessRules.run(checkAdditionalServiceNameDuplication(updateAdditionalServiceRequest.getName()));
		if (result != null) {
			return result;
		}
		
		AdditionalService additionalService = this.additionalServiceDao.getById(updateAdditionalServiceRequest.getId());
		additionalService.setName(updateAdditionalServiceRequest.getName());
		additionalService.setDescription(updateAdditionalServiceRequest.getDescription());
		additionalService.setDailyPrice(updateAdditionalServiceRequest.getDailyPrice());
		
		this.additionalServiceDao.save(additionalService);
		return new SuccessResult(Messages.ADDITIONALSERVICEUPDATE);
	}

	@Override
	public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) {
		AdditionalService additionalService = this.additionalServiceDao.getById(deleteAdditionalServiceRequest.getId());
		
		this.additionalServiceDao.delete(additionalService);
		return new SuccessResult(Messages.ADDITIONALSERVICEDELETE);
	}

	@Override
	public DataResult<List<AdditionalService>> getAll() {
		return new SuccessDataResult<List<AdditionalService>>(this.additionalServiceDao.findAll(), Messages.ADDITIONALSERVICELIST);
	}

	@Override
	public DataResult<AdditionalService> findById(int id) {
		return new SuccessDataResult<AdditionalService>(this.additionalServiceDao.findById(id).get());
	}
	
	public Result checkAdditionalServiceNameDuplication(String name) {

		if (this.additionalServiceDao.existsAdditionalServiceByName(name)) {
			return new ErrorResult(Messages.BRANDNAMEERROR);
		}
		return new SuccessResult();
	}

}
