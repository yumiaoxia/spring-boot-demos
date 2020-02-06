package com.itsherman.commonweb.example.controller;

import com.itsherman.commonweb.example.domain.Person;
import com.itsherman.commonweb.request.ApiRequest;
import com.itsherman.commonweb.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

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
    public ApiResponse<Person> detail(@PathVariable String id) {
        Person person = new Person();
        person.setId(id);
        person.setAge(28);
        person.setBirthday(ZonedDateTime.now());
        person.setName("Sherman");
        return ApiResponse.createSuccess(person);
    }


    @ApiOperation("创建")
    @PostMapping("/exception")
    public ApiResponse<Person> exception(@RequestBody ApiRequest<Person> request) {
        if (request.getCommend() == null) {
            throw new NullPointerException("person must be not null!");
        }
        return ApiResponse.createSuccess(request.getCommend());
    }
}
