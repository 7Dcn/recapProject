package com.etiya.recapProject.entities.dtos;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentalDto {
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date rentDate;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date returnDate;

	private String pickUpLocation;

	private String dropOffLocation;

	private int startKilometer;

	private int endKilometer;
	
	private double amount;
	
	private String carName;

	private String customerEmail;
	
	private List<AdditionalServiceDto> additionalServiceDtos;
}
