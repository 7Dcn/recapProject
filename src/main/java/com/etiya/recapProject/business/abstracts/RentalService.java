package com.etiya.recapProject.business.abstracts;

import java.util.List;

import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.Rental;
import com.etiya.recapProject.entities.requests.CreateRentalRequest;

public interface RentalService {
	Result add(CreateRentalRequest createRentalRequest);
	DataResult<List<Rental>> getAll();
}
