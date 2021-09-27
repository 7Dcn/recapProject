package com.etiya.recapProject.entities.requests.carMaintenanceRequest;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarMaintenanceRequest {

	@NotNull
	private Date maintenanceDate;

	@Size(max = 200)
	private String description;
	
	@NotNull
	private int carId;
	
}
