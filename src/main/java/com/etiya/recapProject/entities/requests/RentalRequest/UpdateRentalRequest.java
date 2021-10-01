package com.etiya.recapProject.entities.requests.rentalRequest;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.etiya.recapProject.entities.concretes.AdditionalService;
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
	private Date rentDate;
	
	@NotNull
	private Date returnDate;
	
	@JsonIgnore
	private int startKilometer;
	
	@Min(0)
	@NotNull
	private int endKilometer;
	
	@NotNull
	private String pickUpLocation;
	
	@NotNull
	private String dropOffLocation;
	
	@NotNull
	private int carId;
	
	@NotNull
	private int customerId;
	
	private boolean rentStatus;
	
	@JsonIgnore
	private double dailyPrice;
	
	@JsonIgnore
	private double totalAmount;
	
	private List<AdditionalService> additionalServices;
}
