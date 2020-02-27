package com.itsherman.dto.assembler.example.web.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.dto.assembler.example.domain.Person;

import java.time.LocalDateTime;

@DtoModel(from = Person.class)
public interface PersonDto {

    Long getId();

    String getName();

    LocalDateTime getBirthday();
}
