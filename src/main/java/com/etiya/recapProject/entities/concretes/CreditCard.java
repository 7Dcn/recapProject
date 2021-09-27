package com.etiya.recapProject.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.etiya.recapProject.entities.abstracts.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "credit_cards")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class CreditCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "card_number")
	private String cardNumber;

	@Column(name = "card_name")
	private String cardName;

	@Column(name = "cvc")
	private String cvc;

	@Column(name = "expiry_date")
	private String expiryDate;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@OneToMany(mappedBy = "creditCard")
	@JsonIgnore
	private List<Payment> payments;
}
