package com.itsherman.modulestest.web.controller;

import com.itsherman.dto.assembler.DtoTransFormer;
import com.itsherman.modulestest.domain.*;
import com.itsherman.modulestest.web.dto.*;
import com.itsherman.web.common.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-04
 */
@Api(tags = "测试Dto接口")
@RequestMapping("/api/interfaces")
@RestController
public class InterfaceController {

    @ApiOperation("person简单信息")
    @GetMapping("/person/detail/simple/{id}")
    public ApiResponse<PersonDto> simple(@PathVariable Long id) {
        Person person = new Person();
        person.setId(id);
        person.setBirthday(LocalDateTime.now().minusYears(26));
        person.setName("Sherman");

        PersonDto personDto = DtoTransFormer.to(PersonDto.class).apply(person);
        return ApiResponse.createSuccess(personDto);
    }


    @ApiOperation("双目标源")
    @GetMapping("/personCompany/{id}")
    public ApiResponse<PersonCompanyDto> personCompany(@PathVariable Long id) {
        Person person = new Person();
        person.setId(id);
        person.setBirthday(LocalDateTime.now().minusYears(27));
        person.setName("Sherman");

        Company company = new Company();
        company.setTitle("道本");
        PersonCompanyDto personCompanyDto = DtoTransFormer.to(PersonCompanyDto.class).apply(person, company);
        return ApiResponse.createSuccess(personCompanyDto);
    }

    @ApiOperation("携带原始类型属性")
    @GetMapping("/person/withRaw/{id}")
    public ApiResponse<PersonWithRawDto> withRaw(@PathVariable Long id) {
        Book book = new Book();
        book.setBookName("Java Dto实战");
        Person person = new Person();
        person.setBook(book);
        person.setId(id);
        person.setName("Sherman");
        return ApiResponse.createSuccess(DtoTransFormer.to(PersonWithRawDto.class).apply(person));
    }

    @ApiOperation("测试默认方法")
    @GetMapping("/person/withDefault/{id}")
    public ApiResponse<PersonWithDefaultDto> withDefault(@PathVariable Long id) {
        Book book = new Book();
        book.setBookName("Java Dto实战");
        Person person = new Person();
        person.setBook(book);
        person.setId(id);
        person.setName("Sherman");
        person.setBirthday(LocalDateTime.now().minusYears(26));
        return ApiResponse.createSuccess(DtoTransFormer.to(PersonWithDefaultDto.class).apply(person));
    }

    @ApiOperation("测试数组")
    @GetMapping("/person/withArray/{id}")
    public ApiResponse<PersonWithArrayDto> withArray(@PathVariable Long id) {
        Book book = new Book();
        book.setBookName("Java Dto实战");
        Person person = new Person();
        person.setId(id);
        person.setName("Sherman");

        String[] hobbies = new String[]{"篮球", "Swimming"};
        person.setHobbies(hobbies);

        Car car1 = new Car();
        car1.setCarName("丰田");
        Car car2 = new Car();
        car2.setCarName("路虎");

        person.setCars(new Car[]{car1, car2});

        return ApiResponse.createSuccess(DtoTransFormer.to(PersonWithArrayDto.class).apply(person));
    }

    @ApiOperation("测试集合")
    @GetMapping("/person/withCollection/{id}")
    public ApiResponse<PersonWithCollectionDto> withCollection(@PathVariable Long id) {

        Person person = new Person();
        person.setId(id);

        Set<House> houses = new HashSet<>();
        House house = new House();
        house.setHouseName("B - 601");
        houses.add(house);

        Set<String> tabs = new HashSet<>();
        tabs.add("腼腆");
        tabs.add("粗心");

        person.setTabs(tabs);
        person.setHouses(houses);

        return ApiResponse.createSuccess(DtoTransFormer.to(PersonWithCollectionDto.class).apply(person));
    }


    @ApiOperation("测试转换enum属性")
    @GetMapping("/person/withEnum/{id}")
    public ApiResponse<PersonDto> withEnum(@PathVariable Long id) {
        Person person = new Person();
        person.setId(id);
        person.setBirthday(LocalDateTime.now().minusYears(26));
        person.setName("Sherman");
        person.setGender(Gender.MALE);

        PersonDto personDto = DtoTransFormer.to(PersonWithEnumDto.class).apply(person);
        return ApiResponse.createSuccess(personDto);
    }

    @ApiOperation("测试集合转换")
    @GetMapping("/person/asList")
    public ApiResponse<List<PersonDto>> personList() {
        List<Person> persons = new ArrayList<>();

        Person person1 = new Person();
        person1.setId(1L);
        person1.setName("张三");

        Person person2 = new Person();
        person2.setId(2L);
        person2.setName("李四");

        persons.add(person1);
        persons.add(person2);
        return ApiResponse.createSuccess(DtoTransFormer.toList(PersonDto.class).apply(persons));

    }
}
