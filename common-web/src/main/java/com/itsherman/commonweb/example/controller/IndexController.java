package com.itsherman.commonweb.example.controller;

import com.itsherman.commonweb.response.ApiResponse;
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

    @ApiOperation("获取详情")
    @GetMapping("/detail/{id}")
    public ApiResponse<Person> detail(@PathVariable String id) {
        Person person = new Person();
        person.setId(id);
        person.setAge(28);
        person.setBirthday(LocalDateTime.now());
        person.setName("Sherman");
        return ApiResponse.createSuccess(person);
    }
}
