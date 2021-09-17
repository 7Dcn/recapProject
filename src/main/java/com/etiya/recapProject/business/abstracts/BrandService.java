package com.etiya.recapProject.business.abstracts;

import java.util.List;

import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.Brand;
import com.etiya.recapProject.entities.requests.CreateBrandRequest;
import com.etiya.recapProject.entities.requests.DeleteBrandRequest;

public interface BrandService {
	Result add(CreateBrandRequest createBrandRequest);
	Result update(CreateBrandRequest createBrandRequest);
	Result delete(DeleteBrandRequest deleteBrandRequest);
	DataResult<List<Brand>> getAll();	
	DataResult<Brand> findById(int id);
//	DataResult<Brand> getByBrandName(String brandName);
}
