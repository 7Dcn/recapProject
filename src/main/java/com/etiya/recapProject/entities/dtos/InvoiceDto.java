package com.etiya.recapProject.entities.dtos;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
	private String invoiceNumber;

    @JsonFormat(pattern = "yyyy-MM-dd hh:MM:ss")
    private Date invoiceDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date rentalDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    //private Date rentalReturnDate;

    private Double amount;

    private List<AdditionalServiceDto> additionalServiceDto;
    
    private String customerEmail;
}
