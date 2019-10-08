package com.itsherman.commondto2.example;

import com.itsherman.commondto2.example.dto.PersonDto;
import com.itsherman.commondto2.example.model.Book;
import com.itsherman.commondto2.example.model.Person;
import com.itsherman.commondto2.manage.DtoTransFormer;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-30
 */
public class DtoAccembleExample {


    public void testDtoAssemble() {
        Person person = new Person();
        person.setAge(18);
        person.setWeight(120L);
        person.setName("鱼子");
        person.setNickName("Sherman");
        person.setBirthday(LocalDateTime.now());
        Book book = new Book();
        book.setName("java 实战");
        book.setVersion(1);
        List<Book> books = new ArrayList<>();
        books.add(book);
        person.setBooks(books);
        Instant start = Instant.now();
        PersonDto personDto = DtoTransFormer.as(PersonDto.class).apply(person).get();
        System.out.println(String.format("编集目标: %s，耗时 %s ms", personDto, String.valueOf(Duration.between(start, Instant.now()).toMillis())));
    }
}
