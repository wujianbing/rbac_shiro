package com.jack.rbac_shiro.config;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * 自定义filter实现单个角色权限
 */
public class CustomRolesOrAuthorizationFilter extends AuthorizationFilter {

    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = this.getSubject(request, response);
        //获取当前路径访问所需要角色集合
        String[] rolesArray = (String[])((String[])mappedValue);
        if(rolesArray == null && rolesArray.length == 0){
            return true;
        }
        if (rolesArray != null && rolesArray.length != 0) {
            Set<String> roles = CollectionUtils.asSet(rolesArray);
            for(String role : roles){
                if(subject.hasRole(role)){
                    return true;
                }
            }

        }
        return false;
    }
}
