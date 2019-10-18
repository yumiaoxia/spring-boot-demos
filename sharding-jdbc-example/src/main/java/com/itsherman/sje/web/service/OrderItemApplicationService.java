package com.itsherman.sje.web.service;

import com.itsherman.sje.service.OrderItemService;
import org.springframework.stereotype.Service;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-10-18
 */
@Service
public class OrderItemApplicationService {

    private final OrderItemService orderItemService;

    public OrderItemApplicationService(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }
}
