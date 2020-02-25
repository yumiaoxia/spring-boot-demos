package com.itsherman.dto.assembler.example.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.dto.assembler.example.domain.Person;

@DtoModel(from = Person.class)
public interface PersonWithArrayDto extends PersonDto {

    CarDto[] getCars();

    String[] getHobbies();
}
