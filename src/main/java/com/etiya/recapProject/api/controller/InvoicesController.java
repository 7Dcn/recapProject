package com.etiya.recapProject.api.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.recapProject.business.abstracts.InvoiceService;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.Invoice;
import com.etiya.recapProject.entities.requests.invoiceRequest.CreateInvoiceRequest;
import com.etiya.recapProject.entities.requests.invoiceRequest.DeleteInvoiceRequest;
import com.etiya.recapProject.entities.requests.invoiceRequest.UpdateInvoiceRequest;

@RestController
@RequestMapping("api/invoicescontroller")
public class InvoicesController {
	private InvoiceService invoiceService;

	@Autowired
	public InvoicesController(InvoiceService invoiceService) {
		super();
		this.invoiceService = invoiceService;
	}

	@PostMapping("/add")
	public Result add(@Valid @RequestBody CreateInvoiceRequest createInvoiceRequest) {
		return this.invoiceService.add(createInvoiceRequest);
	}

	@PostMapping("/update")
	public Result update(@Valid @RequestBody UpdateInvoiceRequest updateInvoiceRequest) {
		return this.invoiceService.update(updateInvoiceRequest);
	}

	@PutMapping("/delete")
	public Result delete(@RequestBody DeleteInvoiceRequest deleteInvoiceRequest) {
		return this.invoiceService.delete(deleteInvoiceRequest);
	}

	@GetMapping("/getall")
	public DataResult<List<Invoice>> getAll() {
		return this.invoiceService.getAll();
	}

	@GetMapping("/getinvoicesbycustomerid")
	public DataResult<List<Invoice>> getCreditCardByCustomer_Id(int customerId) {

		return this.invoiceService.findByCustomerId(customerId);
	}
	@GetMapping("/findinvoicesbetweentwodate")
	public DataResult<List<Invoice>> findInvoicesBetweenTwoDate(@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate) {
		
	System.out.println(endDate.toString()+startDate.toString());
		return this.invoiceService.findInvoicesBetweenTwoDate(endDate,startDate);
	}
}