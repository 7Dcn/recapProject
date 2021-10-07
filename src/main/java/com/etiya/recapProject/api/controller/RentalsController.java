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

import com.etiya.recapProject.business.abstracts.RentalService;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.dtos.RentalDto;
import com.etiya.recapProject.entities.requests.rentalRequest.CreateRentalRequest;
import com.etiya.recapProject.entities.requests.rentalRequest.DeleteRentalRequest;
import com.etiya.recapProject.entities.requests.rentalRequest.UpdateRentalRequest;

@RestController
@RequestMapping("api/rentals")
public class RentalsController {

	private RentalService rentalService;

	@Autowired
	public RentalsController(RentalService rentalService) {
		super();
		this.rentalService = rentalService;
	}

	@PostMapping("/addrentalforindividualcustomer")
	public Result addRentalForIndividualCustomer(@Valid @RequestBody CreateRentalRequest createRentalRequest) {
		return this.rentalService.addRentalForIndividualCustomer(createRentalRequest);
	}

	@PostMapping("/updaterentalforindividualcustomer")
	public Result updateRentalForIndividualCustomer(@Valid @RequestBody UpdateRentalRequest updateRentalRequest) {
		return this.rentalService.updateRentalForIndividualCustomer(updateRentalRequest);
	}

	@PostMapping("/addrentalforcorporatecustomer")
	public Result addRentalForCorporateCustomer(@Valid @RequestBody CreateRentalRequest createRentalRequest) {
		return this.rentalService.addRentalForCorporateCustomer(createRentalRequest);
	}

	@PostMapping("/updaterentalforcopporatecustomer")
	public Result updateRentalForCorporateCustomer(@Valid @RequestBody UpdateRentalRequest updateRentalRequest) {
		return this.rentalService.updateRentalForCorporateCustomer(updateRentalRequest);
	}

	@PutMapping("/delete")
	public Result delete(@Valid @RequestBody DeleteRentalRequest deleteRentalRequest) {

		return this.rentalService.delete(deleteRentalRequest);
	}

	@GetMapping("/getall")
	public DataResult<List<RentalDto>> getAll() {
		return this.rentalService.getAll();
	}

}
