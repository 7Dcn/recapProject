package com.etiya.recapProject.business.abstracts;

import java.util.Date;
import java.util.List;

import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.dtos.InvoiceDto;
import com.etiya.recapProject.entities.requests.invoiceRequest.CreateInvoiceRequest;
import com.etiya.recapProject.entities.requests.invoiceRequest.DeleteInvoiceRequest;
import com.etiya.recapProject.entities.requests.invoiceRequest.UpdateInvoiceRequest;

public interface InvoiceService {

	Result add(CreateInvoiceRequest createInvoiceRequest);

	Result update(UpdateInvoiceRequest updateInvoiceRequest);

	Result delete(DeleteInvoiceRequest deleteInvoiceRequest);

	DataResult<List<InvoiceDto>> getAll();

	DataResult<List<InvoiceDto>> findByCustomerId(int customerId);
	
	DataResult<List<InvoiceDto>> findInvoicesBetweenTwoDate(Date endDate, Date startDate);
}
