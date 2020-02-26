package com.itsherman.dto.assembler.example.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.dto.assembler.example.domain.House;

@DtoModel(from = House.class)
public interface HouseDto {

    String getHouseName();


}
