package com.etiya.recapProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.recapProject.entities.concretes.AdditionalService;

public interface AdditionalServiceDao extends JpaRepository<AdditionalService, Integer> {
	boolean existsAdditionalServiceByName(String name);
	
	List<AdditionalService> findByRentals_Id(int rentalId);
}
