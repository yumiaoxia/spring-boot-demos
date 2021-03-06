package com.itsherman.modulestest.web.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.modulestest.domain.Book;

@DtoModel(from = Book.class)
public class BookDO {

    private String bookName;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
