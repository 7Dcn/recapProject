package com.etiya.recapProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.recapProject.entities.concretes.Damage;

public interface DamageDao extends JpaRepository<Damage, Integer> {

	List<Damage> findByCar_Id(int carId);
}
