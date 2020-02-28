package com.itsherman.modulestest.web.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.modulestest.domain.Gender;
import com.itsherman.modulestest.domain.Person;

@DtoModel(from = {Person.class})
public interface PersonWithEnumDto extends PersonDto {

    Gender getGender();
}
