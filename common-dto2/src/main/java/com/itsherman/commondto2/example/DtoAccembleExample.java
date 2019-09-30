package com.itsherman.commondto2.example;

import com.itsherman.commondto2.example.dto.PersonDto;
import com.itsherman.commondto2.example.model.Person;
import com.itsherman.commondto2.manage.DtoAssembler;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-30
 */
public class DtoAccembleExample {


    public void testDtoAssemble() {
        Person person = new Person();
        PersonDto personDto = DtoAssembler.build().select(PersonDto.class).from(person).as();
    }
}
