package com.itsherman.sje.web.service;

import com.itsherman.commondto2.manage.DtoTransFormer;
import com.itsherman.sje.model.Order;
import com.itsherman.sje.service.OrderService;
import com.itsherman.sje.web.command.OrderCreateCommand;
import com.itsherman.sje.web.dto.OrderDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-10-18
 */
@Service
public class OrderApplicationService {

    private final OrderService orderService;

    public OrderApplicationService(OrderService orderService) {
        this.orderService = orderService;
    }


    @Transactional(rollbackFor = Exception.class)
    public OrderDto create(OrderCreateCommand command) {
        Order order = new Order();
        BeanUtils.copyProperties(command, order);
        Order result = orderService.save(order);
        return DtoTransFormer.as(OrderDto.class).apply(result).orElse(null);
    }
}
