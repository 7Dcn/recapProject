package com.etiya.recapProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.recapProject.entities.concretes.IndividualCustomer;

public interface IndividualCustomerDao extends JpaRepository<IndividualCustomer, Integer> {
	IndividualCustomer getByFirstName(String firstName);
	
	IndividualCustomer getByIdentityNumber(String identityNumber);
}
