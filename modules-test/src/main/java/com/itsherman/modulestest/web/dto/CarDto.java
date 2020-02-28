package com.itsherman.modulestest.web.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.modulestest.domain.Car;

@DtoModel(from = Car.class)
public interface CarDto {

    String getCarName();
}
