package com.itsherman.modulestest.web.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.modulestest.domain.House;

@DtoModel(from = House.class)
public interface HouseDto {

    String getHouseName();


}
