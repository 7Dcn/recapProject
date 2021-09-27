package com.etiya.recapProject.business.abstracts;

import java.util.List;

import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.CarMaintenance;
import com.etiya.recapProject.entities.requests.carMaintenanceRequest.CreateCarMaintenanceRequest;
import com.etiya.recapProject.entities.requests.carMaintenanceRequest.DeleteCarMaintenanceRequest;
import com.etiya.recapProject.entities.requests.carMaintenanceRequest.UpdateCarMaintenanceRequest;

public interface CarMaintenanceService {
	Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest);

	Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest);

	Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest);

	DataResult<List<CarMaintenance>> getAll();
}
