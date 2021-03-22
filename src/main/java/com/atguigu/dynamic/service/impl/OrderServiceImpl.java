package com.atguigu.dynamic.service.impl;

import com.atguigu.dynamic.entity.Order;
import com.atguigu.dynamic.mapper.order.OrderMapper;
import com.atguigu.dynamic.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * OrderServiceImpl层
 *
 * @author szj
 * @since 2021-03-21 16:02
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    //    @Transactional(value = "orderTransactionManager", readOnly = true)
    @Transactional(readOnly = true)
    @Override
    public Order getOrderById(Integer id) {
        return orderMapper.getOrderById(id);
    }

    //    @Transactional("orderTransactionManager")
    @Transactional
    @Override
    public boolean addOrder(Order order) {
        boolean b = orderMapper.addOrder(order);
        int i = 1 / 0;
        return b;
    }


}
