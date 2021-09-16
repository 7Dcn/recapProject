package com.etiya.recapProject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.ColorService;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.ColorDao;
import com.etiya.recapProject.entities.concretes.Color;

@Service
public class ColorManager implements ColorService {

	private ColorDao colorDao;
	
	@Autowired
	public ColorManager(ColorDao colorDao) {
		super();
		this.colorDao = colorDao;
	}
	
	@Override
	public Result add(Color color) {
		this.colorDao.save(color);
		return new SuccessResult(color.getColorName() + " Renk eklendi.");
	}

	@Override
	public Result update(Color color) {
		this.colorDao.save(color);
		return new SuccessResult(color.getColorName() + " Renk güncellendi.");
	}

	@Override
	public Result delete(Color color) {
		this.colorDao.delete(color);
		return new SuccessResult(color.getColorName() + " Renk silindi.");
	}

	@Override
	public DataResult<List<Color>> getAll() {
		return new SuccessDataResult<List<Color>>(this.colorDao.findAll(), "Renkler başarıyla listelendi.");
	}

	@Override
	public DataResult<Color> findById(int id) {
		return new SuccessDataResult<Color>(this.colorDao.findById(id).get());
	}

}
