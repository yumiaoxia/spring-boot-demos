package com.itsherman.sje.web.controller;

import com.itsherman.commonweb.request.ApiRequest;
import com.itsherman.commonweb.response.ApiResponse;
import com.itsherman.sje.web.command.OrderCreateCommand;
import com.itsherman.sje.web.dto.OrderDto;
import com.itsherman.sje.web.service.OrderApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-10-18
 */
@Api(tags = "2-订单")
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderApplicationService orderApplicationService;

    public OrderController(OrderApplicationService orderApplicationService) {
        this.orderApplicationService = orderApplicationService;
    }

    @PostMapping
    @ApiOperation("创建订单")
    public ApiResponse<OrderDto> create(@RequestBody @Validated ApiRequest<OrderCreateCommand> request) {
        OrderDto order = orderApplicationService.create(request.getCommend());
        return ApiResponse.createSuccess(order);
    }
}
