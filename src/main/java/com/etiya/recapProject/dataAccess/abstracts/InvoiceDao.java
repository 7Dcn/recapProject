package com.etiya.recapProject.dataAccess.abstracts;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.recapProject.entities.concretes.Invoice;

public interface InvoiceDao extends JpaRepository<Invoice, Integer> {

	List<Invoice> findByCustomer_Id(int customerId);
	
	List<Invoice> findAllByInvoiceDateLessThanEqualAndInvoiceDateGreaterThanEqual(Date endDate, Date startDate);
}
