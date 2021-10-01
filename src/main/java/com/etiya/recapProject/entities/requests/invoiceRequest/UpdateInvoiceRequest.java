package com.etiya.recapProject.entities.requests.invoiceRequest;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest {
	
	@NotNull
	private int id;
	
	@NotNull
	private String invoiceNumber;
	
	@NotNull
	private Date invoiceDate;
	
	@JsonIgnore
	private Date rentalDate;
	
	@JsonIgnore
	private Date rentalReturnDate;
	
	@JsonIgnore
	private Long totalRentalDay;
	
	@JsonIgnore
	private Double amount;
	
	@NotNull
	private int rentalId;
	
	@NotNull
	private int customerId;
}
