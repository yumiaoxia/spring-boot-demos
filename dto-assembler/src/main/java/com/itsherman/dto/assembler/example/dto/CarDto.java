package com.itsherman.dto.assembler.example.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.dto.assembler.example.domain.Car;

@DtoModel(from = Car.class)
public interface CarDto {

    String getCarName();
}
