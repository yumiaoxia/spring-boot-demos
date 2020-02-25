package com.itsherman.dto.assembler.example.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.dto.assembler.example.domain.Person;

import java.time.LocalDateTime;

@DtoModel(from = Person.class)
public interface PersonWithDefaultDto extends PersonDto {

    default Integer getAge() {
        if (getBirthday() != null) {
            return LocalDateTime.now().getYear() - getBirthday().getYear();
        }
        return 0;
    }
}
