package com.itsherman.sje.web.controller;

import com.itsherman.commonweb.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-10-18
 */
@Api(tags = "1-首页")
@RestController
@RequestMapping("/api/index")
public class IndexController {

    @ApiOperation("测试接口")
    @GetMapping()
    public ApiResponse<String> index() {
        return ApiResponse.createSuccess("Hello world");
    }
}
