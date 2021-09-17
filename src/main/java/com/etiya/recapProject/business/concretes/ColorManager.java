package com.etiya.recapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.ColorService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.ColorDao;
import com.etiya.recapProject.entities.concretes.Color;
import com.etiya.recapProject.entities.requests.CreateColorRequest;
import com.etiya.recapProject.entities.requests.DeleteColorRequest;

@Service
public class ColorManager implements ColorService {

	private ColorDao colorDao;
	
	@Autowired
	public ColorManager(ColorDao colorDao) {
		super();
		this.colorDao = colorDao;
	}
	
	@Override
	public Result add(CreateColorRequest createColorRequest) {
		Color color = new Color();
		color.setColorName(createColorRequest.getColorName());
		
		this.colorDao.save(color);
		return new SuccessResult(Messages.COLORADD);
	}

	@Override
	public Result update(CreateColorRequest CreateColorRequest) {
		Color color = new Color();
		color.setColorName(CreateColorRequest.getColorName());
		
		this.colorDao.save(color);
		return new SuccessResult(Messages.COLORUPDATE);
	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) {
		Color color = new Color();
		color.setId(this.colorDao.getByColorName(deleteColorRequest.getColorName()).getId());
		
		this.colorDao.deleteById(color.getId());
		return new SuccessResult(Messages.COLORDELETE);
	}

	@Override
	public DataResult<List<Color>> getAll() {
		return new SuccessDataResult<List<Color>>(this.colorDao.findAll(), Messages.COLORLIST);
	}

	@Override
	public DataResult<Color> findById(int id) {
		return new SuccessDataResult<Color>(this.colorDao.findById(id).get());
	}

}
