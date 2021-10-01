package com.etiya.recapProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.recapProject.entities.abstracts.ApplicationUser;

public interface ApplicationUserDao extends JpaRepository<ApplicationUser, Integer> {
	boolean existsByEmail(String email);

	ApplicationUser getByEmail(String email);
}
