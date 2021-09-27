package com.etiya.recapProject.entities.requests.carMaintenanceRequest;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCarMaintenanceRequest {
	
	@NotNull
	private int id;
}
