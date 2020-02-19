package com.itsherman.dto.assembler.example.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.dto.assembler.annotations.DtoProperty;
import com.itsherman.dto.assembler.example.domain.Company;
import com.itsherman.dto.assembler.example.domain.Person;

import java.time.LocalDateTime;

@DtoModel(from = {Person.class, Company.class})
public interface PersonCompanyDto {

    @DtoProperty(sourceClass = Company.class)
    String getTitle();

    @DtoProperty(sourceClass = Person.class)
    Long getId();

    LocalDateTime getBirthday();

    String getName();
}
