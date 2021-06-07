package com.cn.momo.system.rbac.service;


import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.rbac.pojo.RbacMenu;
import com.cn.momo.system.rbac.pojo.RbacMenuTree;

import java.util.List;
import java.util.Map;

/**
 * 
 * dongwenmo 2021-01-27
 */
public interface IRbacService {
	/**
	 * 查询用户信息
	 * dongwenmo 2021-01-16
	 */
	List<Map<String, Object>> queryUserInfo(Map<String,Object> map) throws BusinessException;

	/**
	 * 获取用户拥有权限的菜单
	 * dongwenmo 2021-01-27
	 */
	RbacMenuTree getUserMenu(int userId) throws BusinessException;

	/**
	 * 判断某菜单是否在菜单列表中
	 * dongwenmo 2021-01-27
	 */
	boolean isInMenus(List<RbacMenu> menus, Integer menuId);

}
