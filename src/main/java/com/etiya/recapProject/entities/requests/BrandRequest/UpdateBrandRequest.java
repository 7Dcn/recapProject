package com.etiya.recapProject.entities.requests.brandRequest;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBrandRequest {
	
	@NotNull
	private int id;
	
	@NotNull
	private String name;
}
