package com.etiya.recapProject.business.concretes;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.InvoiceService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.CustomerDao;
import com.etiya.recapProject.dataAccess.abstracts.InvoiceDao;
import com.etiya.recapProject.dataAccess.abstracts.RentalDao;
import com.etiya.recapProject.entities.abstracts.Customer;
import com.etiya.recapProject.entities.concretes.Invoice;
import com.etiya.recapProject.entities.concretes.Rental;
import com.etiya.recapProject.entities.requests.invoiceRequest.CreateInvoiceRequest;
import com.etiya.recapProject.entities.requests.invoiceRequest.DeleteInvoiceRequest;
import com.etiya.recapProject.entities.requests.invoiceRequest.UpdateInvoiceRequest;

@Service
public class InvoiceManager implements InvoiceService {

	InvoiceDao invoiceDao;
	RentalDao rentalDao;
	CustomerDao customerDao;

	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, RentalDao rentalDao, CustomerDao customerDao) {
		super();
		this.invoiceDao = invoiceDao;
		this.rentalDao = rentalDao;
		this.customerDao = customerDao;
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {
		long totalRentDay = (ChronoUnit.DAYS.between(
				this.rentalDao.getById(createInvoiceRequest.getRentalId()).getRentDate().toInstant(),
				this.rentalDao.getById(createInvoiceRequest.getRentalId()).getReturnDate().toInstant()));
		
		Customer customer = new Customer();
		customer.setId(createInvoiceRequest.getCustomerId());

		Rental rental = new Rental();
		rental.setId(createInvoiceRequest.getRentalId());

		Invoice invoice = new Invoice();
		invoice.setAmount(totalRentDay*this.rentalDao.getById(createInvoiceRequest.getRentalId()).getCar().getDailyPrice());
		invoice.setInvoiceDate(createInvoiceRequest.getInvoiceDate());
		invoice.setInvoiceNumber(createInvoiceRequest.getInvoiceNumber());

		invoice.setRentalDate(this.rentalDao.getById(createInvoiceRequest.getRentalId()).getRentDate());
		invoice.setRentalReturnDate(this.rentalDao.getById(createInvoiceRequest.getRentalId()).getReturnDate());

		invoice.setTotalRentalDay(totalRentDay);

		invoice.setCustomer(customer);
		invoice.setRental(rental);

		this.invoiceDao.save(invoice);
		return new SuccessResult(Messages.INVOICEADD);
	}

	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) {
		long totalRentDay = (ChronoUnit.DAYS.between(
				this.rentalDao.getById(updateInvoiceRequest.getRentalId()).getRentDate().toInstant(),
				this.rentalDao.getById(updateInvoiceRequest.getRentalId()).getReturnDate().toInstant()));
		
		Customer customer = new Customer();
		customer.setId(updateInvoiceRequest.getCustomerId());

		Rental rental = new Rental();
		rental.setId(updateInvoiceRequest.getRentalId());

		Invoice invoice = this.invoiceDao.getById(updateInvoiceRequest.getId());
		invoice.setAmount(totalRentDay*this.rentalDao.getById(updateInvoiceRequest.getRentalId()).getCar().getDailyPrice());
		invoice.setInvoiceDate(updateInvoiceRequest.getInvoiceDate());
		invoice.setInvoiceNumber(updateInvoiceRequest.getInvoiceNumber());

		invoice.setRentalDate(this.rentalDao.getById(updateInvoiceRequest.getRentalId()).getRentDate());
		invoice.setRentalReturnDate(this.rentalDao.getById(updateInvoiceRequest.getRentalId()).getReturnDate());

		invoice.setTotalRentalDay(totalRentDay);

		invoice.setCustomer(customer);
		invoice.setRental(rental);
		this.invoiceDao.save(invoice);

		return new SuccessResult(Messages.INVOICEUPDATE);
	}

	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) {

		Invoice invoice = this.invoiceDao.getById(deleteInvoiceRequest.getId());

		this.invoiceDao.delete(invoice);
		return new SuccessResult(Messages.INVOICEDELETE);
	}

	@Override
	public DataResult<List<Invoice>> getAll() {

		return new SuccessDataResult<List<Invoice>>(this.invoiceDao.findAll(), Messages.INVOICELIST);
	}

	@Override
	public DataResult<List<Invoice>> findByCustomerId(int customerId) {

		return new SuccessDataResult<List<Invoice>>(this.invoiceDao.findByCustomer_Id(customerId),
				Messages.INVOICEBYCUSTOMERLIST);

	}

	@Override
	public DataResult<List<Invoice>> findInvoicesBetweenTwoDate(Date endDate,
			Date startDate) {
		
		return new SuccessDataResult<List<Invoice>>(this.invoiceDao.findAllByInvoiceDateLessThanEqualAndInvoiceDateGreaterThanEqual(endDate,startDate),
				Messages.INVOICELIST);
	}

}
