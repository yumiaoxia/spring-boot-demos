package com.itsherman.dto.assembler.example.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.dto.assembler.example.domain.Person;

import java.time.LocalDateTime;
import java.util.Set;

@DtoModel(from = Person.class)
public interface PersonDto {

    Long getId();

    String getName();

    BookDto[] getBooks();

    Set<CarDto> getCars();

    String[] getHobbies();

    default Integer getAge() {
        return LocalDateTime.now().getYear() - getBirthday().getYear() + 1;
    }

    LocalDateTime getBirthday();
}
