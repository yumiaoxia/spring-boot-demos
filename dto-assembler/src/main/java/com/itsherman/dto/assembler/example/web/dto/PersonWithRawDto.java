package com.itsherman.dto.assembler.example.web.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.dto.assembler.example.domain.Person;

@DtoModel(from = Person.class)
public interface PersonWithRawDto extends PersonDto {

    BookDto getBook();
}
