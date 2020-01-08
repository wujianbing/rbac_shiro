package com.jack.rbac_shiro.dao;

import com.jack.rbac_shiro.domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from user where username=#{username}")
    User findByUsername(@Param("username")String username);

    @Select("select * from user where id = #{userId}")
    User findById(@Param("userId") int userId);

    @Select("select * from user where username=#{username} and password=#{password}")
    User findByUsernameAndPwd(@Param("username") String username,@Param("password") String password);
}
