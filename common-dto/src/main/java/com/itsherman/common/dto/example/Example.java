package com.itsherman.common.dto.example;

import com.itsherman.common.dto.manage.DtoAssembler;

import java.time.Duration;
import java.time.Instant;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-10
 */
public class Example {

    public void doTransform() {
        Role role = new Role();
        role.setRole("admin");
        Person person = new Person();
        person.setName("Sherman");
        person.setTag("tall");
        person.setAge(18);
        Book book = new Book();
        book.setName("Java 实战");
        person.setBook(book);
        Instant start = Instant.now();
        PersonDto result = (PersonDto) DtoAssembler.builder().select(PersonDto.class).from(person, role).transform();
        long l = Duration.between(start, Instant.now()).toMillis();
        System.out.println("转换结果：" + result + ",共耗时：" + l + " ms");
    }
}
