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

import com.etiya.recapProject.business.abstracts.CreditCardService;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.dtos.CreditCardDto;
import com.etiya.recapProject.entities.requests.creditCardRequest.CreateCreditCardRequest;
import com.etiya.recapProject.entities.requests.creditCardRequest.DeleteCreditCardRequest;
import com.etiya.recapProject.entities.requests.creditCardRequest.UpdateCreditCardRequest;

@RestController
@RequestMapping("api/creditcardscontroller")
public class CreditCardsController {
	private CreditCardService creditCardService;

	@Autowired
	public CreditCardsController(CreditCardService creditCardService) {
		super();
		this.creditCardService = creditCardService;
	}

	@PostMapping("/add")
	public Result add(@Valid @RequestBody CreateCreditCardRequest createCreditCardRequest) {
		return this.creditCardService.add(createCreditCardRequest);
	}

	@PostMapping("/update")
	public Result update(@Valid @RequestBody UpdateCreditCardRequest updateCreditCardRequest) {
		return this.creditCardService.update(updateCreditCardRequest);
	}

	@PutMapping("/delete")
	public Result delete(@RequestBody DeleteCreditCardRequest deleteCreditCardRequest) {
		return this.creditCardService.delete(deleteCreditCardRequest);
	}

	@GetMapping("/getall")
	public DataResult<List<CreditCardDto>> getAll() {
		return this.creditCardService.getAll();
	}

	@GetMapping("/getcreditcardsbycustomerid")
	public DataResult<List<CreditCardDto>> getCreditCardByCustomer_Id(int customerId) {

		return this.creditCardService.getCreditCardByCustomer_Id(customerId);
	}
}