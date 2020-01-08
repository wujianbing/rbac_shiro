package com.jack.rbac_shiro.service;

import com.jack.rbac_shiro.domain.User;

public interface UserService {

    /**
     * 获取全部用户信息，包含角色权限
     * @param username
     * @return
     */
    User findAllUserInfoByUsername(String username);

    /**
     * 获取用户基本信息
     * @param userId
     * @return
     */
    User findSimpleUserInfoById(int userId);

    /**
     * 获取用户基本信息
     * @param username
     * @return
     */
    User findSimpleUserInfoByUsername(String username);
}
