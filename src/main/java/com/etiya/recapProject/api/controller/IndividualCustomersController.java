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

import com.etiya.recapProject.business.abstracts.IndividualCustomerService;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.dtos.IndividualCustomerDto;
import com.etiya.recapProject.entities.requests.individualCustomerRequest.CreateIndividualCustomerRequest;
import com.etiya.recapProject.entities.requests.individualCustomerRequest.DeleteIndividualCustomerRequest;
import com.etiya.recapProject.entities.requests.individualCustomerRequest.UpdateIndividualCustomerRequest;

@RestController
@RequestMapping("api/individualcustomers")
public class IndividualCustomersController {
	private IndividualCustomerService individualCustomerService;

	@Autowired
	public IndividualCustomersController(IndividualCustomerService individualCustomerService) {
		super();
		this.individualCustomerService = individualCustomerService;
	}

	@PostMapping("/add")
	public Result add(@Valid @RequestBody CreateIndividualCustomerRequest createIndividualCustomerRequest) {
		return this.individualCustomerService.add(createIndividualCustomerRequest);
	}
	
	@PostMapping("/update")
	public Result update(@Valid @RequestBody UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		return this.individualCustomerService.update(updateIndividualCustomerRequest);
	}
	
	@PutMapping("/delete")
	public Result delete(@RequestBody DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
		return this.individualCustomerService.delete(deleteIndividualCustomerRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<IndividualCustomerDto>> getAll() {
		return this.individualCustomerService.getAll();
	}
}
