package com.etiya.recapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.BrandService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.BrandDao;
import com.etiya.recapProject.entities.concretes.Brand;
import com.etiya.recapProject.entities.requests.CreateBrandRequest;
import com.etiya.recapProject.entities.requests.DeleteBrandRequest;

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
				
		Brand brand = new Brand();
		brand.setBrandName(createBrandRequest.getBrandName());
		
		this.brandDao.save(brand);
		return new SuccessResult(Messages.BRANDADD);

	}

	@Override
	public Result update(CreateBrandRequest createBrandRequest) {
		Brand brand = new Brand();
		brand.setBrandName(createBrandRequest.getBrandName());
		
		this.brandDao.save(brand);
		return new SuccessResult(Messages.BRANDUPDATE);
	}

	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) {
		Brand brand = new Brand();
		brand.setId(this.brandDao.getByBrandName(deleteBrandRequest.getBrandName()).getId());
		
		this.brandDao.deleteById(brand.getId());
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

}
