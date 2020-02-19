package com.itsherman.dto.assembler.example.controller;

import com.itsherman.dto.assembler.DtoTransFormer;
import com.itsherman.dto.assembler.example.domain.Person;
import com.itsherman.dto.assembler.example.dto.PersonDto;
import com.itsherman.web.common.request.ApiRequest;
import com.itsherman.web.common.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("获取详情")
    @GetMapping("/detail/{id}")
    public ApiResponse<PersonDto> detail(@PathVariable Long id) {
        Person person = new Person();
        person.setId(id);
        person.setBirthday(LocalDateTime.now().minusYears(26));
        person.setName("Sherman");
        PersonDto personDto = DtoTransFormer.to(PersonDto.class).apply(new Person[]{person}).orElse(null);
        return ApiResponse.createSuccess(personDto);
    }


    @ApiOperation("创建")
    @PostMapping("/exception")
    public ApiResponse<Person> exception(@RequestBody ApiRequest<Person> request) {
        if (request.getCommand() == null) {
            throw new NullPointerException("person must be not null!");
        }
        return ApiResponse.createSuccess(request.getCommand());
    }
}
