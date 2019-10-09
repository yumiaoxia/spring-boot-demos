package com.itsherman.commondto2.example.dto;

import com.itsherman.commondto2.annotations.DtoModel;
import com.itsherman.commondto2.annotations.DtoProperty;
import com.itsherman.commondto2.example.dto.dto2.BookDto;
import com.itsherman.commondto2.example.model.Person;

import java.util.Arrays;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-26
 */
@DtoModel(from = Person.class)
public class PersonDto {

    @DtoProperty
    private String name;

    @DtoProperty
    private String[] hobbys;

    @DtoProperty
    private BookDto[] books;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getHobbys() {
        return hobbys;
    }

    public void setHobbys(String[] hobbys) {
        this.hobbys = hobbys;
    }

    public BookDto[] getBooks() {
        return books;
    }

    public void setBooks(BookDto[] books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "PersonDto{" +
                "name='" + name + '\'' +
                ", hobbys=" + Arrays.toString(hobbys) +
                ", books=" + Arrays.toString(books) +
                '}';
    }
}
