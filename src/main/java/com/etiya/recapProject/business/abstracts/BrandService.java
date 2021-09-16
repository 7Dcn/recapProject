package com.etiya.recapProject.business.abstracts;

import java.util.List;

import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.Brand;

public interface BrandService {
	Result add(Brand brand);

	Result update(Brand brand);

	Result delete(Brand brand);

	DataResult<List<Brand>> getAll();
	
	DataResult<Brand> findById(int id);
}
