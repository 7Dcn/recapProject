package com.etiya.recapProject.entities.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

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
	
	@NotNull
	@NotBlank
	@Min(1900)
	private int modelYear;
	
	@NotNull
	@Min(0)
	private double dailyPrice;
	
	@Size(max=200)
	private String description;
	
	@NotNull
	@NotBlank
	private int brandId;
	
	@NotNull
	@NotBlank
	private int colorId;
}
