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

import com.etiya.recapProject.business.abstracts.CarMaintenanceService;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.CarMaintenance;
import com.etiya.recapProject.entities.requests.carMaintenanceRequest.CreateCarMaintenanceRequest;
import com.etiya.recapProject.entities.requests.carMaintenanceRequest.DeleteCarMaintenanceRequest;
import com.etiya.recapProject.entities.requests.carMaintenanceRequest.UpdateCarMaintenanceRequest;

@RestController
@RequestMapping("api/carmaintenances")
public class CarMaintenancesController {
	
	private CarMaintenanceService carMaintenanceService;

	@Autowired
	public CarMaintenancesController(CarMaintenanceService carMaintenanceService) {
		super();
		this.carMaintenanceService = carMaintenanceService;
	}
	
	@PostMapping("/add")
	public Result add(@Valid @RequestBody CreateCarMaintenanceRequest createCarMaintenanceRequest) {
		return this.carMaintenanceService.add(createCarMaintenanceRequest);
	}
	
	@PostMapping("/update")
	public Result update(@Valid @RequestBody UpdateCarMaintenanceRequest updateCarMaintenanceRequest) {
		return this.carMaintenanceService.update(updateCarMaintenanceRequest);
	}
	
	@PutMapping("/delete")
	public Result delete(@Valid @RequestBody DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) {
		return this.carMaintenanceService.delete(deleteCarMaintenanceRequest);
	}
	
	@GetMapping("getall")
	public DataResult<List<CarMaintenance>> getAll(){
		return this.carMaintenanceService.getAll();
	}
	
}
