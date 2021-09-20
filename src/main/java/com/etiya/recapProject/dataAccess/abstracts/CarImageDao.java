package com.etiya.recapProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.recapProject.entities.concretes.CarImage;

public interface CarImageDao extends JpaRepository<CarImage, Integer> {
	int countCarImageByCar_Id(int carId);

	List<CarImage> getByCar_Id(int carId);
	
	CarImage getImagePathByCar_Id(int carId);
}
