package com.atguigu.dynamic.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * Order 库中 Order 实体
 *
 * @author szj
 * @since 2021-03-21 15:55
 */
@Data
@Accessors(chain = true)
public class Order {
    private Integer id;
    private String name;
    private Integer userId;
    private Date createTime;
}
