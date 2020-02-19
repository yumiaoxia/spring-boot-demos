package com.itsherman.dto.assembler.example.dto;

import java.time.LocalDateTime;

//@DtoModel(from = Person.class)
public interface PersonDto {

    Long getId();

    String getName();

    default Integer getAge() {
        return LocalDateTime.now().getYear() - getBirthday().getYear() + 1;
    }

    LocalDateTime getBirthday();
}
