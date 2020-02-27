package com.itsherman.dto.assembler.example.controller;

import com.itsherman.dto.assembler.DtoTransFormer;
import com.itsherman.dto.assembler.example.domain.Book;
import com.itsherman.dto.assembler.example.domain.Car;
import com.itsherman.dto.assembler.example.domain.House;
import com.itsherman.dto.assembler.example.domain.Person;
import com.itsherman.dto.assembler.example.dto.PersonDO;
import com.itsherman.dto.assembler.example.dto.PersonWithArrayDO;
import com.itsherman.dto.assembler.example.dto.PersonWithCollectionDO;
import com.itsherman.dto.assembler.example.dto.PersonWithRawDO;
import com.itsherman.web.common.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Api(tags = "测试Dto类")
@RequestMapping("/api/classes")
@RestController
public class ClassController {

    @ApiOperation("person简单信息")
    @GetMapping("/person/simple/{id}")
    public ApiResponse<PersonDO> simple(@PathVariable Long id) {
        Person person = new Person();
        person.setId(id);
        person.setBirthday(LocalDateTime.now().minusYears(26));
        person.setName("Sherman");

        PersonDO personDO = DtoTransFormer.to(PersonDO.class).apply(person);
        return ApiResponse.createSuccess(personDO);
    }

    @ApiOperation("person带原型属性")
    @GetMapping("/person/withRaw/{id}")
    public ApiResponse<PersonWithRawDO> withRaw(@PathVariable Long id) {
        Person person = new Person();
        person.setId(id);
        person.setBirthday(LocalDateTime.now().minusYears(26));
        person.setName("Sherman");

        Book book = new Book();
        book.setBookName("DO 实战");
        person.setBook(book);
        return ApiResponse.createSuccess(DtoTransFormer.to(PersonWithRawDO.class).apply(person));
    }

    @ApiOperation("person带数组属性")
    @GetMapping("/person/withArray/{id}")
    public ApiResponse<PersonWithArrayDO> withArray(@PathVariable Long id) {
        Person person = new Person();
        person.setId(id);
        person.setBirthday(LocalDateTime.now().minusYears(26));
        person.setName("Sherman");

        Car car1 = new Car();
        car1.setCarName("白马");

        Car car2 = new Car();
        car2.setCarName("黑龙");
        Car[] cars = new Car[]{car1, car2};
        person.setCars(cars);
        return ApiResponse.createSuccess(DtoTransFormer.to(PersonWithArrayDO.class).apply(person));
    }

    @ApiOperation("person带集合属性")
    @GetMapping("/person/withCollection/{id}")
    public ApiResponse<PersonWithCollectionDO> withCollection(@PathVariable Long id) {
        Person person = new Person();
        person.setId(id);
        person.setBirthday(LocalDateTime.now().minusYears(26));
        person.setName("Sherman");


        Set<House> houses = new HashSet<>();
        House house1 = new House();
        house1.setHouseName("白宫");

        House house2 = new House();
        house2.setHouseName("龙阁");

        houses.add(house1);
        houses.add(house2);
        person.setHouses(houses);
        return ApiResponse.createSuccess(DtoTransFormer.to(PersonWithCollectionDO.class).apply(person));
    }

}
