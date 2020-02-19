package com.itsherman.dto.assembler.example.controller;

import com.itsherman.dto.assembler.DtoTransFormer;
import com.itsherman.dto.assembler.example.domain.Company;
import com.itsherman.dto.assembler.example.domain.Person;
import com.itsherman.dto.assembler.example.dto.PersonCompanyDto;
import com.itsherman.dto.assembler.example.dto.PersonDto;
import com.itsherman.web.common.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-04
 */
@Api(tags = "测试接口")
@RequestMapping("/api")
@RestController
public class IndexController {

    @ApiOperation("获取person")
    @GetMapping("/detail/{id}")
    public ApiResponse<PersonDto> detail(@PathVariable Long id) {
        Person person = new Person();
        person.setId(id);
        person.setBirthday(LocalDateTime.now().minusYears(26));
        person.setName("Sherman");
        PersonDto personDto = DtoTransFormer.to(PersonDto.class).apply(new Person[]{person}).orElse(null);
        return ApiResponse.createSuccess(personDto);
    }


    @GetMapping("/personCompany/{id}")
    public ApiResponse<PersonCompanyDto> personCompany(@PathVariable Long id) {
        Person person = new Person();
        person.setId(id);
        person.setBirthday(LocalDateTime.now().minusYears(27));
        person.setName("Sherman");

        Company company = new Company();
        company.setTitle("道本");
        PersonCompanyDto personCompanyDto = DtoTransFormer.to(PersonCompanyDto.class).apply(new Object[]{person, company}).orElse(null);
        return ApiResponse.createSuccess(personCompanyDto);
    }

}
