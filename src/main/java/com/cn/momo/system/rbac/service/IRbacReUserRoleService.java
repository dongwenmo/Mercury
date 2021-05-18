package com.cn.momo.system.rbac.service;

import com.cn.momo.common.IBaseService;
import com.cn.momo.system.rbac.pojo.RbacReUserRole;
import com.cn.momo.system.rbac.pojo.RbacRole;

import java.util.List;

/**
*
* auto 2021-01-26 13:40:37
*/
public interface IRbacReUserRoleService extends IBaseService<RbacReUserRole> {
    /**
     * 用户新增/删除角色
     * dongwenmo 2021-01-26
     */
    int switchUserRole(RbacReUserRole rbacReUserRole);
}
