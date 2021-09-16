package com.etiya.recapProject.business.abstracts;

import java.util.List;

import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.entities.concretes.Color;

public interface ColorService {
	Result add(Color color);

	Result update(Color color);

	Result delete(Color color);

	DataResult<List<Color>> getAll();
	
	DataResult<Color> findById(int id);
}
