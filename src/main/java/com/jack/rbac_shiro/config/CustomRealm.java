package com.jack.rbac_shiro.config;

import com.jack.rbac_shiro.domain.Permission;
import com.jack.rbac_shiro.domain.Role;
import com.jack.rbac_shiro.domain.User;
import com.jack.rbac_shiro.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义realm
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("鉴权");
        User newUser = (User) principalCollection.getPrimaryPrincipal();
        User user = userService.findAllUserInfoByUsername(newUser.getUsername());
        List<String> stringRoleList = new ArrayList<>();
        List<String> stringPermissionList = new ArrayList<>();

        List<Role> roleList = user.getRoleList();
        for(Role role : roleList){
            stringRoleList.add(role.getName());
            List<Permission> permissionList = role.getPermissionList();
            for(Permission permission : permissionList){
                stringPermissionList.add(permission.getName());
            }
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRoles(stringRoleList);
        authorizationInfo.addStringPermissions(stringPermissionList);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String name = (String) authenticationToken.getPrincipal();
        User user = userService.findAllUserInfoByUsername(name);
        String pwd = user.getPassword();
        if(pwd == null || "".equals(pwd)){
            return  null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,pwd,this.getClass().getName());

        return authenticationInfo;
    }
}
