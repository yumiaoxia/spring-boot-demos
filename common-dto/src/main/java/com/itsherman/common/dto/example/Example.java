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
        Person person = new Person();
        person.setName("Sherman");
        person.setAge(18);
        person.setTag("Tall");
        Instant start = Instant.now();
        //PersonDto result = new PersonDto();
        //BeanUtils.copyProperties(person,result);
        PersonDto result = (PersonDto) DtoAssembler.builder().select(PersonDto.class).from(person).transform();
        long l = Duration.between(start, Instant.now()).toMillis();
        System.out.println("转换结果：" + result + ",共耗时：" + l + " ms");
    }
}
