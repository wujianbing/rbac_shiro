package com.jack.rbac_shiro.service.impl;

import com.jack.rbac_shiro.dao.RoleMapper;
import com.jack.rbac_shiro.dao.UserMapper;
import com.jack.rbac_shiro.domain.Role;
import com.jack.rbac_shiro.domain.User;
import com.jack.rbac_shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
   private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;
    @Override
    public User findAllUserInfoByUsername(String username) {
        User user = userMapper.findByUsername(username);

        List<Role> roleList = roleMapper.findRoleListByUserId(user.getId());
        user.setRoleList(roleList);
        return user;
    }

    @Override
    public User findSimpleUserInfoById(int userId) {
        return userMapper.findById(userId);
    }

    @Override
    public User findSimpleUserInfoByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
