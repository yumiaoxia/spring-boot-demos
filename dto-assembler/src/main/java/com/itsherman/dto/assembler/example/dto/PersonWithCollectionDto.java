package com.itsherman.dto.assembler.example.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.dto.assembler.example.domain.Person;

import java.util.Set;

@DtoModel(from = Person.class)
public interface PersonWithCollectionDto {

    Set<HouseDto> getHouses();
}
