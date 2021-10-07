package com.etiya.recapProject.entities.requests.rentalRequest;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.etiya.recapProject.entities.dtos.AdditionalServiceIForRentalDto;
import com.etiya.recapProject.entities.dtos.PaymentDtoForRental;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalRequest {

	@NotNull
	private int id;
	
	@NotNull
	private Date returnDate;
	
	@Min(0)
	@NotNull
	private int endKilometer;
	
	@NotNull
	private String dropOffLocation;
	
	@NotNull
	private int carId;
	
	@NotNull
	private int customerId;
	
	private boolean rentStatus;
	
	@JsonIgnore
	private double totalAmount;
	
	@NotNull
	@Valid
	private PaymentDtoForRental paymentDto;
	
	private List<AdditionalServiceIForRentalDto> additionalServiceDtos;
}
