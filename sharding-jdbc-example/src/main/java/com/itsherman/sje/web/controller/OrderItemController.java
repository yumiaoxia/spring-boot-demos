package com.itsherman.sje.web.controller;

import com.itsherman.sje.web.service.OrderItemApplicationService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-10-18
 */
@Api(tags = "3-订单项")
@RestController
@RequestMapping("/api/orderItem")
public class OrderItemController {

    private final OrderItemApplicationService orderItemApplicationService;

    public OrderItemController(OrderItemApplicationService orderItemApplicationService) {
        this.orderItemApplicationService = orderItemApplicationService;
    }
}
