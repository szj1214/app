package com.atguigu.dynamic.mapper.user;

import com.atguigu.dynamic.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * UserMapper层
 *
 * @author szj
 * @since 2021-03-21 16:01
 */
@Repository
public interface UserMapper {

    @Select("select id, name, age, gender from user where id = #{id}")
    User getUserById(Integer id);

    @Insert("INSERT INTO `user` (name, age, gender) VALUES(#{name}, #{age}, #{gender})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean addUser(User user);
}
