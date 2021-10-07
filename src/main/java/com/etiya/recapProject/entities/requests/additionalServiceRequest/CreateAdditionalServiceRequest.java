package com.etiya.recapProject.entities.requests.additionalServiceRequest;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdditionalServiceRequest {

	@NotNull
	private String name;

	@NotNull
	private double dailyPrice;

	@NotNull
	private String description;

}
