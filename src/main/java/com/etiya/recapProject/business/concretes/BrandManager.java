package com.etiya.recapProject.business.concretes;

import java.util.List;

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
import com.etiya.recapProject.entities.requests.brandRequest.CreateBrandRequest;
import com.etiya.recapProject.entities.requests.brandRequest.DeleteBrandRequest;
import com.etiya.recapProject.entities.requests.brandRequest.UpdateBrandRequest;

@Service
public class BrandManager implements BrandService {

	private BrandDao brandDao;

	@Autowired
	public BrandManager(BrandDao brandDao) {
		super();
		this.brandDao = brandDao;
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) {

		var result = BusinessRules.run(checkBrandNameDuplication(createBrandRequest.getBrandName()));
		if (result != null) {
			return result;
		}
		
		Brand brand = new Brand();
		brand.setBrandName(createBrandRequest.getBrandName());

		this.brandDao.save(brand);
		return new SuccessResult(Messages.BRANDADD);

	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) {		
		Brand brand = this.brandDao.getById(updateBrandRequest.getId());
		brand.setId(updateBrandRequest.getId());
		brand.setBrandName(updateBrandRequest.getBrandName());

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
	public DataResult<List<Brand>> getAll() {
		return new SuccessDataResult<List<Brand>>(this.brandDao.findAll(), Messages.BRANDLIST);
	}

	@Override
	public DataResult<Brand> findById(int id) {
		return new SuccessDataResult<Brand>(this.brandDao.findById(id).get());
	}
	
	public Result checkBrandNameDuplication(String brandName) {

		if (this.brandDao.existsBrandByBrandName(brandName)) {
			return new ErrorResult(Messages.BRANDNAMEERROR);
		}
		return new SuccessResult();
	}

}
