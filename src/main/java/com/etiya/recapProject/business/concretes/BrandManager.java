package com.etiya.recapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.BrandService;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.BrandDao;
import com.etiya.recapProject.entities.concretes.Brand;

@Service
public class BrandManager implements BrandService {

	private BrandDao brandDao;

	@Autowired
	public BrandManager(BrandDao brandDao) {
		super();
		this.brandDao = brandDao;
	}

	@Override
	public Result add(Brand brand) {
		this.brandDao.save(brand);
		return new SuccessResult(brand.getBrandName() + " Marka eklendi.");

	}

	@Override
	public Result update(Brand brand) {
		this.brandDao.save(brand);
		return new SuccessResult(brand.getBrandName() + " Marka güncellendi.");

	}

	@Override
	public Result delete(Brand brand) {
		this.brandDao.delete(brand);
		return new SuccessResult(brand.getBrandName() + " Marka silindi.");
	}

	@Override
	public DataResult<List<Brand>> getAll() {
		return new SuccessDataResult<List<Brand>>(this.brandDao.findAll(), " Markalar listelendi. ");
	}

	@Override
	public DataResult<Brand> findById(int id) {
		return new SuccessDataResult<Brand>(this.brandDao.findById(id).get());
	}

}
