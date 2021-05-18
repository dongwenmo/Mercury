package com.cn.momo.system.rbac.service;

import com.cn.momo.common.IBaseService;
import com.cn.momo.system.rbac.pojo.Menu;

import java.util.List;

/**
 * @author dongwenmo
 * @create 2020-08-02 10:48
 */
public interface IMenuService extends IBaseService<Menu> {
	List<Menu> selectMenuOrderByPriority(int parentId);
	// 上移菜单
	int moveUpMenu(Menu menu);
	// 下移菜单
	int moveDownMenu(Menu menu);
	// 删除菜单，删除后，更新顺序号
	int deleteMenu(int menuId);
}
