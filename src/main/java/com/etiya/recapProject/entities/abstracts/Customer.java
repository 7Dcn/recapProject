package com.etiya.recapProject.entities.abstracts;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.etiya.recapProject.entities.concretes.CreditCard;
import com.etiya.recapProject.entities.concretes.Invoice;
import com.etiya.recapProject.entities.concretes.Rental;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends ApplicationUser {

	@JsonIgnore
	@OneToMany(mappedBy = "customer")
	private List<Rental> rentals;

	@JsonIgnore
	@OneToMany(mappedBy = "customer")
	private List<CreditCard> creditCards;

	@JsonIgnore
	@OneToMany(mappedBy = "customer")
	private List<Invoice> invoices;

}
