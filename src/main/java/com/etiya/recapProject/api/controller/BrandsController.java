package com.etiya.recapProject.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.recapProject.business.abstracts.BrandService;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.Brand;
import com.etiya.recapProject.entities.requests.CreateBrandRequest;
import com.etiya.recapProject.entities.requests.DeleteBrandRequest;


@RestController
@RequestMapping("api/brands")
public class BrandsController {
	
	private BrandService brandService;
	
	@Autowired
	public BrandsController(BrandService brandService) {
		super();
		this.brandService = brandService;
	}

	@PostMapping("/add")
	public Result add(@Valid @RequestBody CreateBrandRequest createBrandRequest) {
		return this.brandService.add(createBrandRequest);
	}
	
	@PostMapping("/update")
	public Result  update(@Valid @RequestBody CreateBrandRequest createBrandRequest) {
		return this.brandService.update(createBrandRequest);
	}
	
	@PutMapping("/delete")
	public Result delete(@RequestBody DeleteBrandRequest deleteBrandRequest) {
		return this.brandService.delete(deleteBrandRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<Brand>> getAll() {
		return this.brandService.getAll();
	}
	
	@GetMapping("/getbyid")
	public DataResult<Brand> getById(int id) {
		return this.brandService.findById(id);
	}
}
