package com.etiya.recapProject.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;	
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.recapProject.business.abstracts.DamageService;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.dtos.DamageDto;
import com.etiya.recapProject.entities.requests.damageRequest.CreateDamageRequest;
import com.etiya.recapProject.entities.requests.damageRequest.DeleteDamageRequest;
import com.etiya.recapProject.entities.requests.damageRequest.UpdateDamageRequest;

@RestController
@RequestMapping("api/damages")
public class DamagesController {

	private DamageService damageService;

	@Autowired
	public DamagesController(DamageService damageService) {
		super();
		this.damageService = damageService;
	}

	@PostMapping("/add")
	public Result add(@Valid @RequestBody CreateDamageRequest createDamageRequest) {
		return this.damageService.add(createDamageRequest);
	}

	@PostMapping("/update")
	public Result update(@Valid @RequestBody UpdateDamageRequest updateDamageRequest) {
		return this.damageService.update(updateDamageRequest);
	}

	@PutMapping("/delete")
	public Result delete(@RequestBody DeleteDamageRequest deleteDamageRequest) {
		return this.damageService.delete(deleteDamageRequest);
	}

	@GetMapping("/getall")
	public DataResult<List<DamageDto>> getAll() {
		return this.damageService.getAll();
	}

	@GetMapping("/getbycarid")
	public DataResult<List<DamageDto>> getById(int carId) {
		return this.damageService.findByCarId(carId);
	}
}