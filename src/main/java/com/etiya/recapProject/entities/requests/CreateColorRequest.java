package com.etiya.recapProject.entities.requests;

import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateColorRequest {
	
	@NotNull
	@NotBlank
	private String colorName;
}
