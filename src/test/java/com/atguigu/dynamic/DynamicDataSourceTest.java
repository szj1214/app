package com.atguigu.dynamic;

import com.atguigu.dynamic.entity.Order;
import com.atguigu.dynamic.entity.User;
import com.atguigu.dynamic.service.OrderService;
import com.atguigu.dynamic.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * 动态数据源测试
 *
 * @author szj
 * @since 2021-03-21 17:00
 */
@SpringBootTest
class DynamicDataSourceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Test
    void selectOrder() {
        Order order = orderService.getOrderById(1);
        System.out.println(order);
    }

    @Test
    void selectUser() {
        User user = userService.getUserById(1);
        System.out.println(user);
    }

    @Test
    void addOrder() {
        Order order = new Order();
        order.setName("香蕉").setUserId(1).setCreateTime(new Date());
        orderService.addOrder(order);
    }

    @Test
    void addUser() {
        User user = new User();
        user.setName("李四").setAge(18).setGender("女");
        userService.addUser(user);
    }

    /**
     * java.lang.ArithmeticException: / by zero:出现异常，此时事务回滚出现问题，user表回滚，order表没有回滚，需要引入分布式事务
     * 本次使用Atomikos+jta方案来实现分布式事务，具体操作为：
     * 1.在创建数据源时使用 MysqlXADataSource，使用Druid数据库连接池时可以使用 DruidXADataSource （XA数据源是支持分布式事务的数据源）
     * 2.将创建好的数据源交给Atomikos管理，Atomikos会接管分布式事务
     * 3.再次测试，实现分布式事务，异常时两个表都回滚
     */
    @Test
    void addAll() {
        User user = new User();
        user.setName("王五").setAge(28).setGender("男");
        Order order = new Order();
        order.setName("鸭梨").setUserId(1).setCreateTime(new Date());
        userService.addAll(user, order);
    }
}
