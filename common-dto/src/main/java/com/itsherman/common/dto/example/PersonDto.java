package com.itsherman.common.dto.example;

import com.itsherman.common.dto.annotations.DtoMapping;
import com.itsherman.common.dto.annotations.DtoProperty;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-10
 */
@DtoMapping(from = {Person.class})
public class PersonDto {

    @DtoProperty(value = "tag", sourceClass = Person.class)
    private String name;

    @DtoProperty(sourceClass = Person.class)
    private Integer age;

    @DtoProperty(sourceClass = Person.class)
    private BookDto book;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "PersonDto{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", book=" + book +
                '}';
    }
}
