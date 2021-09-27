package com.etiya.recapProject.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int Id;

	@ManyToOne
	@JoinColumn(name = "rental_id")
	private Rental rental;

	@ManyToOne
	@JoinColumn(name = "credit_card_id")
	private CreditCard creditCard;
}
