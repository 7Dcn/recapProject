package com.etiya.recapProject.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.recapProject.business.abstracts.ApplicationUserService;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.entities.concretes.ApplicationUser;

@RestController
@RequestMapping("api/users")
public class ApplicationUsersController {
	
	@Autowired
	private ApplicationUserService userService;
	
	public ApplicationUsersController(ApplicationUserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/getall")
	public DataResult<List<ApplicationUser>> getAll() {
		return this.userService.getAll();
	}
}
