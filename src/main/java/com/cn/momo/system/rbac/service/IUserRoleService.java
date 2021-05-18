package com.cn.momo.system.rbac.service;

import com.cn.momo.common.IBaseService;
import com.cn.momo.system.rbac.pojo.UserRole;

import java.util.Map;

/**
 * @author dongwenmo
 * @create 2020-07-30 12:45
 */
public interface IUserRoleService extends IBaseService<UserRole> {
	// 新增或修改角色信息
	Map<String, Object> addAndModifyUserRole(UserRole userRole);
}
