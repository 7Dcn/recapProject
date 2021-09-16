package com.etiya.recapProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.recapProject.entities.concretes.Brand;

public interface BrandDao extends JpaRepository<Brand, Integer> {

}
