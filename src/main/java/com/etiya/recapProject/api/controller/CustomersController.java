package com.etiya.recapProject.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.recapProject.business.abstracts.CustomerService;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.Customer;
import com.etiya.recapProject.entities.requests.CreateCustomerRequest;
import com.etiya.recapProject.entities.requests.DeleteCustomerRequest;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("api/customers")
public class CustomersController {
	private CustomerService customerService;

	@Autowired
	public CustomersController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateCustomerRequest createCustomerRequest) {
		return this.customerService.add(createCustomerRequest);
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody Customer customer) {
		return this.customerService.update(customer);
	}
	
	@PutMapping("/delete")
	public Result delete(@RequestBody DeleteCustomerRequest deleteCustomerRequest) {
		return this.customerService.delete(deleteCustomerRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<Customer>> getAll() {
		return this.customerService.getAll();
	}
}
