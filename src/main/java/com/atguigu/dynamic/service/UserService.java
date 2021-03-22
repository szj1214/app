package com.atguigu.dynamic.service;

import com.atguigu.dynamic.entity.Order;
import com.atguigu.dynamic.entity.User;

/**
 * UserService层
 *
 * @author szj
 * @since 2021-03-21 16:01
 */
public interface UserService {
    User getUserById(Integer id);

    boolean addUser(User user);

    boolean addAll(User user, Order order);
}
