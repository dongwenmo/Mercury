package com.cn.momo.system.rbac.service;

import com.cn.momo.common.IBaseService;
import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.rbac.dto.MoveMenuDTO;
import com.cn.momo.system.rbac.pojo.RbacMenu;
import com.cn.momo.system.rbac.pojo.RbacMenuTree;

import java.util.List;

/**
 * 权限菜单
 * dongwenmo 2021-01-27
 */
public interface IRbacMenuService extends IBaseService<RbacMenu> {
    /**
     * 通过菜单id获取权限菜单树
     * dongwenmo 2021-01-20
     */
    RbacMenuTree getTreeById(RbacMenuTree tree);

    /**
     * 获取某菜单的子菜单列表
     * dongwenmo 2021-01-21
     */
    List<RbacMenuTree> getTreeChildren(RbacMenuTree tree);

    /**
     * 通过菜单列表获取权限菜单树
     * 根节点的父id为0
     * dongwenmo 2021-01-20
     */
    RbacMenuTree getTreeByList(List<RbacMenu> menuList, RbacMenuTree tree);

    /**
     * 通过菜单列表获取子菜单列表
     * dongwenmo 2021-01-27
     */
    List<RbacMenuTree> getTreeChildrenByList(List<RbacMenu> menuList, RbacMenuTree tree);

    /**
     * 根据主键删除菜单及其子孙菜单
     * dongwenmo 2021-01-21
     */
    int deleteByPrimaryKey(Integer key);

    /**
     * 移动菜单节点
     * dongwenmo 2021-01-21
     */
    void moveMenuNode(MoveMenuDTO moveMenuDTO) throws BusinessException;

    /**
     * 根据优先级排序
     * dongwenmo 2021-01-22
     */
    List<RbacMenu> sortRbacMenus(List<RbacMenu> rbacMenus);

}
