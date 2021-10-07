package com.etiya.recapProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recapProject.business.abstracts.ColorService;
import com.etiya.recapProject.business.constants.Messages;
import com.etiya.recapProject.core.business.BusinessRules;
import com.etiya.recapProject.core.utilities.results.DataResult;
import com.etiya.recapProject.core.utilities.results.ErrorResult;
import com.etiya.recapProject.core.utilities.results.Result;
import com.etiya.recapProject.core.utilities.results.SuccessDataResult;
import com.etiya.recapProject.core.utilities.results.SuccessResult;
import com.etiya.recapProject.dataAccess.abstracts.ColorDao;
import com.etiya.recapProject.entities.concretes.Color;
import com.etiya.recapProject.entities.dtos.ColorDto;
import com.etiya.recapProject.entities.requests.colorRequest.CreateColorRequest;
import com.etiya.recapProject.entities.requests.colorRequest.DeleteColorRequest;
import com.etiya.recapProject.entities.requests.colorRequest.UpdateColorRequest;

@Service
public class ColorManager implements ColorService {

	private ColorDao colorDao;
	private ModelMapper modelMapper;

	@Autowired
	public ColorManager(ColorDao colorDao, ModelMapper modelMapper) {
		super();
		this.colorDao = colorDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) {

		var result = BusinessRules.run(checkColorName(createColorRequest.getName()));
		if (result != null) {
			return result;
		}

		Color color = modelMapper.map(createColorRequest, Color.class);

		this.colorDao.save(color);
		return new SuccessResult(Messages.COLORADD);
	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest) {
		Color color = modelMapper.map(updateColorRequest, Color.class);
		this.colorDao.save(color);

		return new SuccessResult(Messages.COLORUPDATE);
	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) {
		Color color = this.colorDao.getById(deleteColorRequest.getId());

		this.colorDao.delete(color);
		return new SuccessResult(Messages.COLORDELETE);
	}

	@Override
	public DataResult<List<ColorDto>> getAll() {

		List<Color> colors = this.colorDao.findAll();
		List<ColorDto> result = colors.stream().map(color -> modelMapper.map(color, ColorDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ColorDto>>(result, Messages.COLORLIST);
	}

	@Override
	public DataResult<ColorDto> findById(int id) {
		Color color = this.colorDao.findById(id).get();
		ColorDto colorDto = modelMapper.map(color, ColorDto.class);

		return new SuccessDataResult<ColorDto>(colorDto);
	}

	public Result checkColorName(String name) {

		if (this.colorDao.existsColorByName(name)) {
			return new ErrorResult(Messages.COLORNAMEERROR);
		}
		return new SuccessResult();
	}

}
