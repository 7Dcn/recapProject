package com.etiya.recapProject.business.concretes;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.AdditionalServiceService;
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
import com.etiya.recapProject.entities.dtos.InvoiceDto;
import com.etiya.recapProject.entities.requests.invoiceRequest.CreateInvoiceRequest;
import com.etiya.recapProject.entities.requests.invoiceRequest.DeleteInvoiceRequest;
import com.etiya.recapProject.entities.requests.invoiceRequest.UpdateInvoiceRequest;

@Service
public class InvoiceManager implements InvoiceService {

	private InvoiceDao invoiceDao;
	private RentalDao rentalDao;
	private CustomerDao customerDao;
	private ModelMapper modelMapper;
	private AdditionalServiceService additionalServiceService;

	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, RentalDao rentalDao, CustomerDao customerDao, ModelMapper modelMapper,
			AdditionalServiceService additionalServiceService) {
		super();
		this.invoiceDao = invoiceDao;
		this.rentalDao = rentalDao;
		this.customerDao = customerDao;
		this.modelMapper = modelMapper;
		this.additionalServiceService = additionalServiceService;
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {
		long totalRentDay = (ChronoUnit.DAYS.between(
				this.rentalDao.getById(createInvoiceRequest.getRentalId()).getRentDate().toInstant(),
				this.rentalDao.getById(createInvoiceRequest.getRentalId()).getReturnDate().toInstant()));

		Customer customer = this.customerDao.getById(createInvoiceRequest.getCustomerId());

		Rental rental = this.rentalDao.getById(createInvoiceRequest.getRentalId());

		Invoice invoice = new Invoice();
		invoice.setAmount(rental.getAmount());
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

		Customer customer = this.customerDao.getById(updateInvoiceRequest.getCustomerId());

		Rental rental = this.rentalDao.getById(updateInvoiceRequest.getRentalId());

		Invoice invoice = this.invoiceDao.getById(updateInvoiceRequest.getId());
		invoice.setAmount(rental.getAmount());
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
	public DataResult<List<InvoiceDto>> getAll() {
		List<Invoice> invoices = this.invoiceDao.findAll();

		List<InvoiceDto> result = new ArrayList<InvoiceDto>();

		for (Invoice invoiceItem : invoices) {
			InvoiceDto invoiceDto = modelMapper.map(invoiceItem, InvoiceDto.class);
			invoiceDto.setAdditionalServiceDto(
					this.additionalServiceService.findByRentals_Id(invoiceItem.getRental().getId()).getData());
			result.add(invoiceDto);
		}

		return new SuccessDataResult<List<InvoiceDto>>(result, Messages.INVOICELIST);
	}

	@Override
	public DataResult<List<InvoiceDto>> findByCustomerId(int customerId) {
		List<Invoice> invoices = this.invoiceDao.findByCustomer_Id(customerId);

		List<InvoiceDto> result = new ArrayList<InvoiceDto>();

		for (Invoice invoiceItem : invoices) {
			InvoiceDto invoiceDto = modelMapper.map(invoiceItem, InvoiceDto.class);
			invoiceDto.setAdditionalServiceDto(
					this.additionalServiceService.findByRentals_Id(invoiceItem.getRental().getId()).getData());
			result.add(invoiceDto);
		}

		return new SuccessDataResult<List<InvoiceDto>>(result, Messages.INVOICELIST);
	}

	@Override
	public DataResult<List<InvoiceDto>> findInvoicesBetweenTwoDate(Date endDate, Date startDate) {
		List<Invoice> invoices = this.invoiceDao
				.findAllByInvoiceDateLessThanEqualAndInvoiceDateGreaterThanEqual(endDate, startDate);

		List<InvoiceDto> result = new ArrayList<InvoiceDto>();

		for (Invoice invoiceItem : invoices) {
			InvoiceDto invoiceDto = modelMapper.map(invoiceItem, InvoiceDto.class);
			invoiceDto.setAdditionalServiceDto(
					this.additionalServiceService.findByRentals_Id(invoiceItem.getRental().getId()).getData());
			result.add(invoiceDto);
		}

		return new SuccessDataResult<List<InvoiceDto>>(result, Messages.INVOICELIST);
	}

}
