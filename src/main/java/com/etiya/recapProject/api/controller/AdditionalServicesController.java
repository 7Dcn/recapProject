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

import com.etiya.recapProject.business.abstracts.AdditionalServiceService;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.dtos.AdditionalServiceDto;
import com.etiya.recapProject.entities.requests.additionalServiceRequest.CreateAdditionalServiceRequest;
import com.etiya.recapProject.entities.requests.additionalServiceRequest.DeleteAdditionalServiceRequest;
import com.etiya.recapProject.entities.requests.additionalServiceRequest.UpdateAdditionalServiceRequest;

@RestController
@RequestMapping("api/additionalservice")
public class AdditionalServicesController {

	private AdditionalServiceService additionalServiceService;

	@Autowired
	public AdditionalServicesController(AdditionalServiceService additionalServiceService) {
		super();
		this.additionalServiceService = additionalServiceService;
	}
	

	@PostMapping("/add")
	public Result add(@Valid @RequestBody CreateAdditionalServiceRequest createAdditionalServiceRequest) {
		return this.additionalServiceService.add(createAdditionalServiceRequest);
	}
	
	@PostMapping("/update")
	public Result  update(@Valid @RequestBody UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
		return this.additionalServiceService.update(updateAdditionalServiceRequest);
	}
	
	@PutMapping("/delete")
	public Result delete(@RequestBody DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) {
		return this.additionalServiceService.delete(deleteAdditionalServiceRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<AdditionalServiceDto>> getAll() {
		return this.additionalServiceService.getAll();
	}
	
	@GetMapping("/getbyid")
	public DataResult<AdditionalServiceDto> getById(int id) {
		return this.additionalServiceService.findById(id);
	}
}
