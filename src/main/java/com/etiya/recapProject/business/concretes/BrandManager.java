package com.etiya.recapProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.BrandService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.business.BusinessRules;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.ErrorResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.BrandDao;
import com.etiya.recapProject.entities.concretes.Brand;
import com.etiya.recapProject.entities.dtos.BrandDto;
import com.etiya.recapProject.entities.requests.brandRequest.CreateBrandRequest;
import com.etiya.recapProject.entities.requests.brandRequest.DeleteBrandRequest;
import com.etiya.recapProject.entities.requests.brandRequest.UpdateBrandRequest;

@Service
public class BrandManager implements BrandService {

	private BrandDao brandDao;
	private ModelMapper modelMapper;

	@Autowired
	public BrandManager(BrandDao brandDao, ModelMapper modelMapper) {
		super();
		this.brandDao = brandDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) {

		var result = BusinessRules.run(checkBrandNameDuplication(createBrandRequest.getName()));
		if (result != null) {
			return result;
		}

		Brand brand = modelMapper.map(createBrandRequest, Brand.class);

		this.brandDao.save(brand);
		return new SuccessResult(Messages.BRANDADD);

	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) {
		Brand brand = modelMapper.map(updateBrandRequest, Brand.class);

		this.brandDao.save(brand);
		return new SuccessResult(Messages.BRANDUPDATE);
	}

	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) {
		Brand brand = this.brandDao.getById(deleteBrandRequest.getId());

		this.brandDao.delete(brand);
		return new SuccessResult(Messages.BRANDDELETE);
	}

	@Override
	public DataResult<List<BrandDto>> getAll() {
		List<Brand> brands = this.brandDao.findAll();

		List<BrandDto> result = brands.stream().map(brand -> modelMapper.map(brand, BrandDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<BrandDto>>(result, Messages.BRANDLIST);
	}

	@Override
	public DataResult<BrandDto> findById(int id) {
		Brand brand = this.brandDao.findById(id).get();
		BrandDto brandDto = modelMapper.map(brand, BrandDto.class);

		return new SuccessDataResult<BrandDto>(brandDto);
	}

	public Result checkBrandNameDuplication(String name) {

		if (this.brandDao.existsBrandByName(name)) {
			return new ErrorResult(Messages.BRANDNAMEERROR);
		}
		return new SuccessResult();
	}

}
