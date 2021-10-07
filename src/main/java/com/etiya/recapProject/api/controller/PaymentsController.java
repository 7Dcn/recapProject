package com.etiya.recapProject.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.recapProject.business.abstracts.PaymentService;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.dtos.PaymentDto;
import com.etiya.recapProject.entities.requests.paymentRequest.CreatePaymentRequest;

@RestController
@RequestMapping("api/payments")
public class PaymentsController {
	
	private PaymentService paymentService;

	@Autowired
	public PaymentsController(PaymentService paymentService) {
		super();
		this.paymentService = paymentService;
	}
	
	@PostMapping("/add")
	public Result add(@Valid @RequestBody CreatePaymentRequest createPaymentRequest) {
		return this.paymentService.add(createPaymentRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<PaymentDto>> getAll() {
		return this.paymentService.getAll();
	}

}
