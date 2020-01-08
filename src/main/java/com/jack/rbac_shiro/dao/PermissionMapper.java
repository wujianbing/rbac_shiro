package com.jack.rbac_shiro.dao;

import com.jack.rbac_shiro.domain.Permission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionMapper {

    @Select("select p.id as id,p.name as name,p.url as url from role_permission rp left join permission p on p.id = rp.permission_id where rp.role_id = #{roleId}")
    List<Permission> findPermissionByRoleId(@Param("roleId") int roleId);
}
