package com.etiya.recapProject.entities.concretes;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.etiya.recapProject.entities.abstracts.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rentals")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "rent_date")
	private Date rentDate;

	@Column(name = "return_date")
	@Nullable
	private Date returnDate;

	@Column(name = "return_status")
	private boolean returnStatus;

	@Column(name = "pick_up_location")
	private String pickUpLocation;

	@Column(name = "drop_off_location")
	private String dropOffLocation;

	@Column(name = "start_kilometer")
	private int startKilometer;

	@Column(name = "end_kilometer")
	private int endKilometer;

	@Column(name = "dailyPrice")
	private double dailyPrice;
	
	@Column(name = "amount")
	private double amount;
	
	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "invoice_id")
	private Invoice invoice;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE}, mappedBy = "additionalServices")
	private List<AdditionalService> additionalServices;
			
}
