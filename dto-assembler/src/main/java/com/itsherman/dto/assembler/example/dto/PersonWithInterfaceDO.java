package com.itsherman.dto.assembler.example.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.dto.assembler.example.domain.Person;

@DtoModel(from = Person.class)
public class PersonWithInterfaceDO extends PersonDO {

    private BookDto book;

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }
}
