package com.etiya.recapProject.entities.requests.damageRequest;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDamageRequest {

	@NotNull
	private int id;

	@NotNull
	private int carId;

	@NotNull
	private String information;

}
