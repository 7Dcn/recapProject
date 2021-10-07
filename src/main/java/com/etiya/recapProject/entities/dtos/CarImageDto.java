package com.etiya.recapProject.entities.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarImageDto {
	
	private String imageName;

	private String imagePath;

	private LocalDate date;

	private String carName;
}
