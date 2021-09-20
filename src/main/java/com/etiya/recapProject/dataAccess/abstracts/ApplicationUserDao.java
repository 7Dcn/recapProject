package com.etiya.recapProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.recapProject.entities.concretes.ApplicationUser;

public interface ApplicationUserDao extends JpaRepository<ApplicationUser, Integer> {

}
