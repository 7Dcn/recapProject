package com.etiya.recapProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.recapProject.entities.concretes.Color;
import com.etiya.recapProject.entities.dtos.CarDetailDto;

public interface ColorDao extends JpaRepository<Color, Integer> {
	Color getByColorName(String colorName);
	
	List<CarDetailDto> getCarsByColorName(String colorName);
}
