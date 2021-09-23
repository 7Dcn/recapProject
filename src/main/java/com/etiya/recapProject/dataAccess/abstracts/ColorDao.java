package com.etiya.recapProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.recapProject.entities.concretes.Color;

public interface ColorDao extends JpaRepository<Color, Integer> {
	
	boolean existsColorByColorName(String colorName);
}
