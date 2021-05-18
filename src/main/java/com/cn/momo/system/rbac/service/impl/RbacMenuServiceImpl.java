package com.cn.momo.system.rbac.service.impl;

import com.cn.momo.common.BaseServiceImpl;
import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.rbac.dto.MoveMenuDTO;
import com.cn.momo.system.rbac.mapper.RbacMenuMapper;
import com.cn.momo.system.rbac.pojo.RbacMenu;
import com.cn.momo.system.rbac.pojo.RbacMenuTree;
import com.cn.momo.system.rbac.service.IRbacMenuService;
import com.cn.momo.util.CheckUtil;
import com.cn.momo.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限菜单
 * dongwenmo 2021-01-20
 */
@Service
public class RbacMenuServiceImpl extends BaseServiceImpl<RbacMenu> implements IRbacMenuService {
    @Autowired
    private RbacMenuMapper rbacMenuMapper;

    @Override
    public RbacMenuTree getTreeById(RbacMenuTree tree) {
        List<RbacMenuTree> list = getTreeChildren(tree);
        for (RbacMenuTree i : list) {
            getTreeById(i);
            tree.getChildren().add(i);
        }
        return tree;
    }

    @Override
    public List<RbacMenuTree> getTreeChildren(RbacMenuTree tree) {
        int parentId = tree.getMenuId();
        RbacMenu menu = new RbacMenu();
        menu.setMenuParentId(parentId);
        List<RbacMenu> menus = sortRbacMenus(rbacMenuMapper.select(menu));
        List<RbacMenuTree> menuTrees = new ArrayList<>();
        for (RbacMenu i : menus) {
            menuTrees.add(new RbacMenuTree(i));
        }
        return menuTrees;
    }

    @Override
    public RbacMenuTree getTreeByList(List<RbacMenu> menuList, RbacMenuTree tree) {
        List<RbacMenuTree> list = getTreeChildrenByList(menuList, tree);
        for (RbacMenuTree i : list) {
            getTreeByList(menuList, i);
            tree.getChildren().add(i);
        }
        return tree;
    }

    @Override
    public List<RbacMenuTree> getTreeChildrenByList(List<RbacMenu> menuList, RbacMenuTree tree) {
        List<RbacMenu> menus = new ArrayList<>();
        for(RbacMenu i:menuList){
            if(i.getMenuParentId().equals(tree.getMenuId())){
                menus.add(i);
            }
        }
        menus = sortRbacMenus(menus);

        List<RbacMenuTree> menuTrees = new ArrayList<>();
        for (RbacMenu i : menus) {
            menuTrees.add(new RbacMenuTree(i));
        }
        return menuTrees;
    }

    @Override
    public int insertSelective(RbacMenu rbacMenu) {
        Integer parentId = rbacMenu.getMenuParentId();
        if (parentId == null) {
            parentId = 0;
        }
        RbacMenu parent = new RbacMenu();
        parent.setMenuParentId(parentId);
        int count = rbacMenuMapper.selectCount(parent);
        rbacMenu.setPriority(count + 1);

        return rbacMenuMapper.insertSelective(rbacMenu);
    }


    @Override
    public int deleteByPrimaryKey(Integer key) {
        RbacMenu menu = new RbacMenu();
        menu.setMenuParentId(key);
        List<RbacMenu> menus = rbacMenuMapper.select(menu);
        for (RbacMenu i : menus) {
            deleteByPrimaryKey(i.getMenuId());
        }

        return rbacMenuMapper.deleteByPrimaryKey(key);
    }

    @Override
    public void moveMenuNode(MoveMenuDTO moveMenuDTO) throws BusinessException {
        // 入参
        int menuId = moveMenuDTO.getMenuId();
        int menuAimId = moveMenuDTO.getMenuAimId();
        String type = moveMenuDTO.getType();
        CheckUtil.isIn(moveMenuDTO.getType(), "移动类型","before","after","inner");

        // 变量
        int priority = 1;// 优先级
        RbacMenu selectBrothers;// 查询载体
        List<RbacMenu> aimBrothers;// 目标位变量

        // 将原节点存入缓存
        RbacMenu menu = rbacMenuMapper.selectByPrimaryKey(menuId);
        RbacMenu aimMenu = rbacMenuMapper.selectByPrimaryKey(menuAimId);

        // 插入到新位置
        if ("inner".equals(type)) {
            // 在节点内部
            selectBrothers = new RbacMenu();
            selectBrothers.setMenuParentId(aimMenu.getMenuId());
            aimBrothers = sortRbacMenus(rbacMenuMapper.select(selectBrothers));

            // 插入
            menu.setMenuParentId(aimMenu.getMenuId());
            rbacMenuMapper.updateByPrimaryKey(menu);

            aimBrothers.add(menu);
        }else{
            // 在节点前后
            // 获取兄弟节点
            selectBrothers = new RbacMenu();
            selectBrothers.setMenuParentId(aimMenu.getMenuParentId());
            aimBrothers = sortRbacMenus(rbacMenuMapper.select(selectBrothers));

            if(menu.getMenuParentId().equals(aimMenu.getMenuParentId())){
                for (int i = 0; i < aimBrothers.size(); i++) {
                    if (aimBrothers.get(i).getMenuId().equals(menu.getMenuId())) {
                        aimBrothers.remove(i);
                        break;
                    }
                }
            }

            // 插入
            menu.setMenuParentId(aimMenu.getMenuParentId());
            rbacMenuMapper.updateByPrimaryKey(menu);

            // 获取目标位置顺序号
            priority = 0;
            for (int i = 0; i < aimBrothers.size(); i++) {
                if (aimBrothers.get(i).getMenuId().equals(aimMenu.getMenuId())) {
                    priority = i;
                    break;
                }
            }

            if ("before".equals(type)) {
                // 在目标节点前
                aimBrothers.add(priority, menu);
            } else if ("after".equals(type)) {
                // 在目标节点后
                aimBrothers.add(priority+1, menu);
            }else{
                throw new BusinessException("【"+type+"】未知的操作");
            }
        }
        // 重新排序新节点位置，并保存
        priority = 1;
        for (RbacMenu i : aimBrothers) {
            i.setPriority(priority++);
            rbacMenuMapper.updateByPrimaryKey(i);
            System.out.println(JsonUtil.toJson(i));
        }

        // 重新排序原节点兄弟节点，并保存
        int menuParentId = menu.getMenuParentId();
        selectBrothers = new RbacMenu();
        selectBrothers.setMenuParentId(menuParentId);
        List<RbacMenu> brothers = sortRbacMenus(rbacMenuMapper.select(selectBrothers));

        // 保存
        priority = 1;
        for (RbacMenu i : brothers) {
            i.setPriority(priority++);
            rbacMenuMapper.updateByPrimaryKey(i);
        }

    }

    @Override
    public List<RbacMenu> sortRbacMenus(List<RbacMenu> lists) {
        int len = lists.size();
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len - 1 - i; j++) {
                if (lists.get(j).getPriority() > lists.get(j + 1).getPriority()) {
                    RbacMenu temp = lists.get(j);
                    lists.set(j, lists.get(j + 1));
                    lists.set(j + 1, temp);
                }
            }
        }
        return lists;
    }
}
