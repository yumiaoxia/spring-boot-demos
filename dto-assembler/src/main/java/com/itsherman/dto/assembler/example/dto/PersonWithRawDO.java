package com.itsherman.dto.assembler.example.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.dto.assembler.example.domain.Person;

@DtoModel(from = Person.class)
public class PersonWithRawDO extends PersonDO {

    private BookDO book;

    public BookDO getBook() {
        return book;
    }

    public void setBook(BookDO book) {
        this.book = book;
    }
}
