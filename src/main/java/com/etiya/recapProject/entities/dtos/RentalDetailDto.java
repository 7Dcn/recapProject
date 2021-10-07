package com.etiya.recapProject.entities.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentalDetailDto {

	private int id;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date returnDate;
}

