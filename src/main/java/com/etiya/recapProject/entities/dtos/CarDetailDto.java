package com.etiya.recapProject.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDetailDto {
	
	private int carId;
	
	private String brandName;
	
	private String colorName;
	
	private double dailyPrice;
}
