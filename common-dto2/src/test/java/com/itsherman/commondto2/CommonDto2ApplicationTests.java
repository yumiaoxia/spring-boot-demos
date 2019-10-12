package com.itsherman.commondto2;

import com.itsherman.commondto2.example.DtoAccembleExample;
import com.itsherman.commondto2.example.dto.PersonDto;
import com.itsherman.commondto2.example.dto.dto2.BookDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonDto2ApplicationTests {

    @Test
    public void contextLoads() {
        DtoAccembleExample example = new DtoAccembleExample();
        example.testDtoAssemble();
    }

    @Test
    public void testDtoInvoke() {
        PersonDto personDto = new PersonDto();
        BookDto[] books = new BookDto[2];
        BookDto book = new BookDto();
        book.setName("Java");
        book.setVersion(2);
        BookDto book2 = new BookDto();
        book2.setName("python");
        book2.setVersion(3);
        books[0] = book;
        books[1] = book2;
        Class dtoCls = personDto.getClass();
        try {
            Object value = books;
            Method method = dtoCls.getMethod("setBooks", BookDto[].class);
            method.invoke(personDto, value);
            System.out.println(personDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void otherTest() {
        Integer a1 = 128;
        Integer a2 = 128;
        System.out.println((a1.equals(a2)) + " " + a1 + " " + a2);
    }

}
