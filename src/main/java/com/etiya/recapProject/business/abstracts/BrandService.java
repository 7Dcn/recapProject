package com.etiya.recapProject.business.abstracts;

import java.util.List;

import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.Brand;
import com.etiya.recapProject.entities.requests.BrandRequest.CreateBrandRequest;
import com.etiya.recapProject.entities.requests.BrandRequest.DeleteBrandRequest;
import com.etiya.recapProject.entities.requests.BrandRequest.UpdateBrandRequest;

public interface BrandService {
	Result add(CreateBrandRequest createBrandRequest);

	Result update(UpdateBrandRequest updateBrandRequest);

	Result delete(DeleteBrandRequest deleteBrandRequest);

	DataResult<List<Brand>> getAll();

	DataResult<Brand> findById(int id);
	
}
