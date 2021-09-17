package com.etiya.recapProject.entities.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {
	
	private String carName;
	
	private int modelYear;
	
	private double dailyPrice;
	
	private String description;
	
	private int brandId;
	
	private int colorId;
}
