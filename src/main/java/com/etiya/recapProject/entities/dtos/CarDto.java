package com.etiya.recapProject.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
	private String name;

	private String brandName;

	private String colorName;

	private double dailyPrice;
}
