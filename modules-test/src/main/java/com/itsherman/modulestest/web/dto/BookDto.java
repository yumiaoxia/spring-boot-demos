package com.itsherman.modulestest.web.dto;
import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.modulestest.domain.Book;

@DtoModel(from = {Book.class})
public interface BookDto {

    String getBookName();
}
