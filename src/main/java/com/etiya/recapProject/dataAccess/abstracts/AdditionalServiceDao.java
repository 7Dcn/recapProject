package com.etiya.recapProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.recapProject.entities.concretes.AdditionalService;

public interface AdditionalServiceDao extends JpaRepository<AdditionalService, Integer> {
	boolean existsAdditionalServiceByName(String name);
}
