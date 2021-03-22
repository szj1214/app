package com.atguigu.dynamic.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * User 库中 User实体
 *
 * @author szj
 * @since 2021-03-21 15:55
 */
@Data
@Accessors(chain = true)
public class User {
    private Integer id;
    private String name;
    private Integer age;
    private String gender;
}
