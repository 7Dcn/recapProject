package com.etiya.recapProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.etiya.recapProject.entities.concretes.Car;
import com.etiya.recapProject.entities.dtos.CarDetailDto;

public interface CarDao extends JpaRepository<Car, Integer> {
	@Query("Select new com.etiya.recapProject.entities.dtos.CarDetailDto"
			+ " (c.carName, b.brandName, co.colorName, c.dailyPrice) " 
			+ " From Brand b Inner Join b.cars c "
			+ " Inner Join c.color co")
	List<CarDetailDto> getCarsWithBrandAndColorDetails();
	
	Car getByCarName(String carName);
	
	@Query("Select new com.etiya.recapProject.entities.dtos.CarDetailDto"
			+ " (c.carName, b.brandName, co.colorName, c.dailyPrice) " 
			+ " From Brand b Inner Join b.cars c "
			+ " Inner Join c.color co Where b.brandName=:brandName")
	List<CarDetailDto> getCarsByBrandName(String brandName);
	
	@Query("Select new com.etiya.recapProject.entities.dtos.CarDetailDto"
			+ " (c.carName, b.brandName, co.colorName, c.dailyPrice) " 
			+ " From Brand b Inner Join b.cars c "
			+ " Inner Join c.color co Where co.colorName=:colorName")
	List<CarDetailDto> getCarsByColorName(String colorName);
}
