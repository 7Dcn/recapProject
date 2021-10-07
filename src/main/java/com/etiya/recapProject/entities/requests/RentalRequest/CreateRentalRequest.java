package com.etiya.recapProject.entities.requests.rentalRequest;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
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
public class CreateRentalRequest {

	@JsonIgnore
	private int id;
	
	@NotNull
	private Date rentDate;
	
	@NotNull
	private Date returnDate;

	@NotNull
	private int carId;

	@NotNull
	private int customerId;

	@JsonIgnore
	private int startKilometer;

	@JsonIgnore
	private String pickUpLocation;

	@NotNull
	private String dropOffLocation;
	
	@NotNull
	@Valid
	private PaymentDtoForRental paymentDto;
	
	private List<AdditionalServiceIForRentalDto> additionalServiceDtos;
}
