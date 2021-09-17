package com.etiya.recapProject.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="customers")
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends User {
	
	@Column(name="company_name")
	private String companyName;
	
	@JsonIgnore
	@OneToMany(mappedBy = "customer")
	private List<Rental> rentals;
	
}
