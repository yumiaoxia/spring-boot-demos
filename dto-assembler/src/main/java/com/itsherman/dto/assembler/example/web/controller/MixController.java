package com.itsherman.dto.assembler.example.web.controller;

import com.itsherman.dto.assembler.DtoTransFormer;
import com.itsherman.dto.assembler.example.domain.Book;
import com.itsherman.dto.assembler.example.domain.House;
import com.itsherman.dto.assembler.example.domain.Person;
import com.itsherman.dto.assembler.example.web.dto.PersonWithClassCollectionDto;
import com.itsherman.dto.assembler.example.web.dto.PersonWithInterfaceCollectionDO;
import com.itsherman.dto.assembler.example.web.dto.PersonWithInterfaceDO;
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

@Api(tags = "混合测试Dto类接口+类")
@RequestMapping("/api/mix")
@RestController
public class MixController {

    @ApiOperation("测试类带接口属性")
    @GetMapping("/person/withInterface/{id}")
    public ApiResponse<PersonWithInterfaceDO> ClassWithInterface(@PathVariable Long id) {
        Person person = new Person();
        person.setId(id);
        person.setBirthday(LocalDateTime.now().minusYears(26));
        person.setName("Sherman");

        Book book = new Book();
        book.setBookName("DO 实战");
        person.setBook(book);
        return ApiResponse.createSuccess(DtoTransFormer.to(PersonWithInterfaceDO.class).apply(person));
    }

    @ApiOperation("测试接口带类集合属性")
    @GetMapping("/person/withClassCollection/{id}")
    public ApiResponse<PersonWithClassCollectionDto> withClassCollection(@PathVariable Long id) {
        Person person = new Person();
        person.setId(id);

        Set<House> houses = new HashSet<>();
        House house = new House();
        house.setHouseName("B - 601");
        houses.add(house);
        houses.add(house);
        houses.add(null);

        Set<String> tabs = new HashSet<>();
        tabs.add("腼腆");
        tabs.add("粗心");

        person.setTabs(tabs);
        person.setHouses(houses);
        return ApiResponse.createSuccess(DtoTransFormer.to(PersonWithClassCollectionDto.class).apply(person));
    }

    @ApiOperation("测试类带接口集合属性")
    @GetMapping("/person/withInterfaceCollection/{id}")
    public ApiResponse<PersonWithInterfaceCollectionDO> withInterfaceCollection(@PathVariable Long id) {
        Person person = new Person();
        person.setId(id);

        Set<House> houses = new HashSet<>();
        House house = new House();
        house.setHouseName("B - 601");
        houses.add(house);
        houses.add(house);
        houses.add(null);

        Set<String> tabs = new HashSet<>();
        tabs.add("腼腆");
        tabs.add("粗心");

        person.setTabs(tabs);
        person.setHouses(houses);
        return ApiResponse.createSuccess(DtoTransFormer.to(PersonWithInterfaceCollectionDO.class).apply(person));
    }
}
