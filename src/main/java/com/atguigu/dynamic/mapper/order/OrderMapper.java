package com.atguigu.dynamic.mapper.order;

import com.atguigu.dynamic.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * OrderMapper层
 *
 * @author szj
 * @since 2021-03-21 16:01
 */
@Repository
public interface OrderMapper {

    @Select("SELECT id, name, user_id, create_time FROM `order` where id = #{id}")
    Order getOrderById(Integer id);

    @Insert("INSERT INTO `order` (name, user_id, create_time) VALUES(#{name}, #{userId}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean addOrder(Order order);
}


