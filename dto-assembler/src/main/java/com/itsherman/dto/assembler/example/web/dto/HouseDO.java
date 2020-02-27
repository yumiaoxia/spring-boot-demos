package com.itsherman.dto.assembler.example.web.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.dto.assembler.annotations.DtoProperty;
import com.itsherman.dto.assembler.example.domain.House;

@DtoModel(from = House.class)
public class HouseDO {


    private String houName;

    public String getHouName() {
        return houName;
    }

    @DtoProperty("houseName")
    public void setHouName(String houName) {
        this.houName = houName;
    }
}
