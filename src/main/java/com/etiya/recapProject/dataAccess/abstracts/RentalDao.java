package com.etiya.recapProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.etiya.recapProject.entities.concretes.Rental;
import com.etiya.recapProject.entities.dtos.RentalDetailDto;

public interface RentalDao extends JpaRepository<Rental, Integer> {
	@Query("Select new com.etiya.recapProject.entities.dtos.RentalDetailDto"
			+ "(c.id, r.returnDate) " 
			+ 	"From Car c Inner Join c.rentals r where c.id=:carId and r.returnDate is null")
	RentalDetailDto getByCarIdWhereReturnDateIsNull(int carId);
}
