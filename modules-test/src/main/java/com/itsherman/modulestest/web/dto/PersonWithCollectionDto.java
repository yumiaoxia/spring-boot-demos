package com.itsherman.modulestest.web.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.modulestest.domain.Person;

import java.util.Set;

@DtoModel(from = Person.class)
public interface PersonWithCollectionDto extends PersonDto {

    Set<HouseDto> getHouses();

    Set<String> getTabs();


}
