package com.itsherman.dto.assembler.example.dto;

import com.itsherman.dto.assembler.annotations.DtoModel;
import com.itsherman.dto.assembler.example.domain.Book;

@DtoModel(from = {Book.class})
public interface BookDto {

    String getBookName();
}
