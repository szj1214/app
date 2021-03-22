package com.atguigu.dynamic.service;

import com.atguigu.dynamic.entity.Order;

/**
 * OrderService层
 *
 * @author szj
 * @since 2021-03-21 16:01
 */
public interface OrderService {
    Order getOrderById(Integer id);

    boolean addOrder(Order order);
}
