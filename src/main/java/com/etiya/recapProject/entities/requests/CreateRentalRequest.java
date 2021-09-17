package com.etiya.recapProject.entities.requests;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalRequest {
	private Date rentDate;
	
	private Date returnDate;
	
	private int carId;
	
	private int customerId;
}
