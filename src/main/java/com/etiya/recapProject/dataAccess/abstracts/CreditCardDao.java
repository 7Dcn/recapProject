package com.etiya.recapProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.recapProject.entities.concretes.CreditCard;

public interface CreditCardDao extends JpaRepository<CreditCard, Integer> {
	List<CreditCard> getCreditCardByCustomer_Id(int customerId);
}
