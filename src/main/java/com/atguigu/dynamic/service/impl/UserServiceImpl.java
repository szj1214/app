package com.atguigu.dynamic.service.impl;

import com.atguigu.dynamic.entity.Order;
import com.atguigu.dynamic.entity.User;
import com.atguigu.dynamic.mapper.order.OrderMapper;
import com.atguigu.dynamic.mapper.user.UserMapper;
import com.atguigu.dynamic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserServiceImpl层
 *
 * @author szj
 * @since 2021-03-21 16:02
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    OrderMapper orderMapper;

    //    @Transactional(value = "userTransactionManager", readOnly = true)
    @Transactional(readOnly = true)
    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    //    @Transactional("userTransactionManager")
    @Transactional
    @Override
    public boolean addUser(User user) {
        boolean b = userMapper.addUser(user);
        int i = 1 / 0;
        return b;
    }

    @Transactional
    @Override
    public boolean addAll(User user, Order order) {
        boolean a = userMapper.addUser(user);
        boolean b = orderMapper.addOrder(order);
        int i = 1 / 0;
        return a && b;
    }
}
