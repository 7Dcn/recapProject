package com.etiya.recapProject.business.abstracts;

import java.util.List;

import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.Color;
import com.etiya.recapProject.entities.requests.colorRequest.CreateColorRequest;
import com.etiya.recapProject.entities.requests.colorRequest.DeleteColorRequest;
import com.etiya.recapProject.entities.requests.colorRequest.UpdateColorRequest;

public interface ColorService {
	Result add(CreateColorRequest createColorRequest);

	Result update(UpdateColorRequest updateColorRequest);

	Result delete(DeleteColorRequest deleteColorRequest);

	DataResult<List<Color>> getAll();

	DataResult<Color> findById(int id);
}
