package com.etiya.recapProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
import com.etiya.recapProject.entities.dtos.AdditionalServiceDto;
import com.etiya.recapProject.entities.requests.additionalServiceRequest.CreateAdditionalServiceRequest;
import com.etiya.recapProject.entities.requests.additionalServiceRequest.DeleteAdditionalServiceRequest;
import com.etiya.recapProject.entities.requests.additionalServiceRequest.UpdateAdditionalServiceRequest;

@Service
public class AdditionalServiceManager implements AdditionalServiceService {

	private AdditionalServiceDao additionalServiceDao;
	private ModelMapper modelMapper;
	
	@Autowired
	public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapper modelMapper) {
		super();
		this.additionalServiceDao = additionalServiceDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) {
		var result = BusinessRules.run(checkAdditionalServiceNameDuplication(createAdditionalServiceRequest.getName()));
		if (result != null) {
			return result;
		}
		
		AdditionalService additionalService = modelMapper.map(createAdditionalServiceRequest, AdditionalService.class);
		
		this.additionalServiceDao.save(additionalService);
		return new SuccessResult(Messages.ADDITIONALSERVICEADD);
	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
		var result = BusinessRules.run(checkAdditionalServiceNameDuplication(updateAdditionalServiceRequest.getName()));
		if (result != null) {
			return result;
		}
		
		AdditionalService additionalService = modelMapper.map(updateAdditionalServiceRequest, AdditionalService.class);
		
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
	public DataResult<List<AdditionalServiceDto>> getAll() {
		List<AdditionalService> additionalServices = this.additionalServiceDao.findAll();
		
		List<AdditionalServiceDto> result = additionalServices.stream().map(this::convertToDto).collect(Collectors.toList());
		
		return new SuccessDataResult<List<AdditionalServiceDto>>(result, Messages.ADDITIONALSERVICELIST);
	}

	@Override
	public DataResult<AdditionalServiceDto> findById(int id) {
		AdditionalService additionalService = this.additionalServiceDao.findById(id).get();
		AdditionalServiceDto additionalServiceDto = modelMapper.map(additionalService, AdditionalServiceDto.class);
		
		return new SuccessDataResult<AdditionalServiceDto>(additionalServiceDto);
	}
	
	public Result checkAdditionalServiceNameDuplication(String name) {

		if (this.additionalServiceDao.existsAdditionalServiceByName(name)) {
			return new ErrorResult(Messages.BRANDNAMEERROR);
		}
		return new SuccessResult();
	}
	
	private AdditionalServiceDto convertToDto(AdditionalService additionalService) {
		AdditionalServiceDto additionalServiceDto = modelMapper.map(additionalService, AdditionalServiceDto.class);
		return additionalServiceDto;
	}

	@Override
	public DataResult<List<AdditionalServiceDto>> findByRentals_Id(int rentalId) {
		List<AdditionalService> additionalServices = this.additionalServiceDao.findByRentals_Id(rentalId);
        List<AdditionalServiceDto> additionalServiceDtos = additionalServices.stream()
                .map(additionalService -> modelMapper.map(additionalService, AdditionalServiceDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<AdditionalServiceDto>>(additionalServiceDtos);
	}

	

}
