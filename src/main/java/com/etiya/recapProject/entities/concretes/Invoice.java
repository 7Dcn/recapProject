package com.etiya.recapProject.entities.concretes;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.etiya.recapProject.entities.abstracts.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "invoices")
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "invoice_number")
	private String invoiceNumber;

	@Column(name = "invoice_date")
	private Date invoiceDate;

	@Column(name = "rental_date")
	private Date rentalDate;

	@Column(name = "rental_return_date")
	private Date rentalReturnDate;

	@Column(name = "total_rental_day")
	private Long totalRentalDay;

	@Column(name = "amount")
	private Double amount;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "rental_id")
	private Rental rental;

}
