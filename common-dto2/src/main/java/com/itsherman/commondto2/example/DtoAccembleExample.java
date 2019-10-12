package com.itsherman.commondto2.example;

import com.itsherman.commondto2.example.dto.PersonDto;
import com.itsherman.commondto2.example.model.Book;
import com.itsherman.commondto2.example.model.Person;
import com.itsherman.commondto2.manage.DtoTransFormer;

import java.time.Duration;
import java.time.Instant;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-30
 */
public class DtoAccembleExample {


    public void testDtoAssemble() {
        Person person = new Person();
        Book book = new Book();
        book.setName("java 实战");
        book.setVersion(1);
        Book book1 = new Book();
        book1.setName("java 基础");
        book1.setVersion(3);
        person.setName("Sherman");
        Book[] books = new Book[2];
        books[0] = book;
        books[1] = book1;
        person.setBooks(books);
        Instant start = Instant.now();
        PersonDto personDto = DtoTransFormer.as(PersonDto.class).apply(person).get();
        System.out.println(String.format("编集目标: %s，耗时 %s ms", personDto, String.valueOf(Duration.between(start, Instant.now()).toMillis())));
    }
}
