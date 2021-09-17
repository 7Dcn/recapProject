package com.etiya.recapProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.recapProject.entities.concretes.Rental;

public interface RentalDao extends JpaRepository<Rental, Integer> {
	boolean existsByReturnStatusIsFalse();
}
