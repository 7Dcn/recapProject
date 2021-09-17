package com.etiya.recapProject.entities.requests;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalRequest {
	
	@NotNull
	@NotBlank
	private Date rentDate;
	
	private Date returnDate;
	
	@NotNull
	@NotBlank
	private int carId;
	
	@NotNull
	@NotBlank
	private int customerId;
	
	private boolean rentStatus;
}
