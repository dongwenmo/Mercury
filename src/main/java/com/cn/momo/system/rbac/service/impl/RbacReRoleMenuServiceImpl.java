package com.cn.momo.system.rbac.service.impl;

import com.cn.momo.common.BaseServiceImpl;
import com.cn.momo.system.rbac.mapper.RbacReRoleMenuMapper;
import com.cn.momo.system.rbac.pojo.RbacMenu;
import com.cn.momo.system.rbac.pojo.RbacReRoleMenu;
import com.cn.momo.system.rbac.service.IRbacMenuService;
import com.cn.momo.system.rbac.service.IRbacReRoleMenuService;
import com.cn.momo.util.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
*
* auto 2021-01-25 18:56:10
*/
@Service
public class RbacReRoleMenuServiceImpl extends BaseServiceImpl<RbacReRoleMenu> implements IRbacReRoleMenuService {
    @Autowired
    private RbacReRoleMenuMapper rbacReRoleMenuMapper;
    @Autowired
    private IRbacMenuService iRbacMenuService;

    @Override
    public int switchMenuAuthority(RbacReRoleMenu rbacReRoleMenu) {
        RbacReRoleMenu re = new RbacReRoleMenu();
        re.setRoleId(rbacReRoleMenu.getRoleId());
        re.setMenuId(rbacReRoleMenu.getMenuId());

        // 增加权限
        List<RbacReRoleMenu> menus = rbacReRoleMenuMapper.select(re);
        if(menus.size() == 0){
            // 插入
            rbacReRoleMenuMapper.insertSelective(rbacReRoleMenu);
        }else if(menus.size() == 1){
            // 修改
            rbacReRoleMenu.setReId(menus.get(0).getReId());
            rbacReRoleMenuMapper.updateByPrimaryKey(rbacReRoleMenu);
        }else{
            // 先删后插
            rbacReRoleMenuMapper.delete(re);
            rbacReRoleMenuMapper.insertSelective(rbacReRoleMenu);
        }
        return 1;
    }

    @Override
    public List<Integer> getMenuAuthority(int roleId) {
        List<Integer> menuIds = new ArrayList<>();
        List<Integer> disMenuIds = new ArrayList<>();

        RbacReRoleMenu re = new RbacReRoleMenu();
        re.setRoleId(roleId);
        re.setType("1");
        List<RbacReRoleMenu> list = rbacReRoleMenuMapper.select(re);

        for(RbacReRoleMenu i:list){
            menuIds.add(i.getMenuId());

            RbacMenu vMenu = new RbacMenu();
            vMenu.setMenuParentId(i.getMenuId());
            List<RbacMenu> menus = iRbacMenuService.select(vMenu);
            for(RbacMenu j:menus){
                menuIds.add(j.getMenuId());
            }
        }

        // 去除禁止权限
        re.setType("2");
        list = rbacReRoleMenuMapper.select(re);
        for(RbacReRoleMenu i:list){
            disMenuIds.add(i.getMenuId());
        }

        menuIds = MathUtil.forAAtoA(menuIds);
        disMenuIds = MathUtil.forAAtoA(disMenuIds);

        return MathUtil.inAnotB(menuIds,disMenuIds);
    }
}
