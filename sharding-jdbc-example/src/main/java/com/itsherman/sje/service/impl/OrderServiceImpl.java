package com.itsherman.sje.service.impl;

import com.itsherman.sje.dao.OrderRepository;
import com.itsherman.sje.model.Order;
import com.itsherman.sje.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-10-18
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
