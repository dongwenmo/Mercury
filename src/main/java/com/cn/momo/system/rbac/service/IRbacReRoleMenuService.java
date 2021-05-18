package com.cn.momo.system.rbac.service;

import com.cn.momo.common.IBaseService;
import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.rbac.pojo.RbacReRoleMenu;

import java.util.List;

/**
*
* auto 2021-01-25 18:56:10
*/
public interface IRbacReRoleMenuService extends IBaseService<RbacReRoleMenu> {
    /**
     * 变更菜单权限
     * dongwenmo 2021-01-25
     */
    int switchMenuAuthority(RbacReRoleMenu rbacReRoleMenu) throws BusinessException;

    /**
     * 获取菜单权限
     * dongwenmo 2021-01-26
     */
    List<Integer> getMenuAuthority(int roleId);
}
