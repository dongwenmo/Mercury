package com.cn.momo.system.rbac.service.impl;

import com.cn.momo.util.sql.SQL;
import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.rbac.pojo.RbacMenu;
import com.cn.momo.system.rbac.pojo.RbacMenuTree;
import com.cn.momo.system.rbac.service.IRbacMenuService;
import com.cn.momo.system.rbac.service.IRbacReRoleMenuService;
import com.cn.momo.system.rbac.service.IRbacService;
import com.cn.momo.util.MathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 权限系统服务
 * dongwenmo 2021-01-25
 */
@Service
public class RbacServiceImpl implements IRbacService {
    @Autowired
    private IRbacReRoleMenuService iRbacReRoleMenuService;
    @Autowired
    private IRbacMenuService iRbacMenuService;

    @Override
    public List<Map<String, Object>> queryUserInfo(Map<String, Object> map) throws BusinessException {
        SQL sql = new SQL();

        sql.addSql("  select a.user_id,a.username,a.nick_name,a.create_time,a.identity_id,  ");
        sql.addSql("         (select sum(1) login_count                                ");
        sql.addSql("            from user_login c                                      ");
        sql.addSql("           where c.flag = '0'                                      ");
        sql.addSql("             and c.user_id = a.user_id) login_count,               ");
        sql.addSql("         (select b.login_time from user_login b                    ");
        sql.addSql("           where b.flag = '0'                                      ");
        sql.addSql("             and b.user_id = a.user_id                             ");
        sql.addSql("           order by b.login_time desc                              ");
        sql.addSql("           limit 1) last_login_time                                ");
        sql.addSql("    from user a                                                    ");
        sql.addSql("   order by login_count desc                                       ");

        return sql.query();
    }

    @Override
    public RbacMenuTree getUserMenu(int userId) throws BusinessException {
        SQL sql = new SQL();
        List<Integer> menuIds = new ArrayList<>();

        // 获取用户拥有的角色
        sql.clear();
        sql.addSql("  select a.role_id roleId       ");
        sql.addSql("    from rbac_re_user_role a,   ");
        sql.addSql("         rbac_role b            ");
        sql.addSql("   where a.role_id = b.role_id  ");
        sql.addSql("     and b.state = '1'          ");
        sql.addSql("     and a.user_id = ?          ");

        sql.setPara(userId);
        List<Map<String, Object>> list = sql.query();

        for (Map<String, Object> i : list) {
            // 获取角色拥有的菜单
            List<Integer> ids = iRbacReRoleMenuService.getMenuAuthority((int) i.get("roleId"));
            // 取并集、去重
            menuIds = MathUtil.inAandB(menuIds, ids);
        }

        // 获取菜单信息
        List<RbacMenu> menus = new ArrayList<>();
        for (Integer i : menuIds) {
            RbacMenu menu = iRbacMenuService.selectByPrimaryKey(i);
            if (menu != null) {
                menus.add(menu);
            }
        }

        // 补齐中间节点
        int index = 0;
        while (index < menus.size()) {
            RbacMenu vMenu = menus.get(index);
            if (vMenu.getMenuParentId() != 0 && !isInMenus(menus, vMenu.getMenuParentId())) {
                RbacMenu menu = iRbacMenuService.selectByPrimaryKey(vMenu.getMenuParentId());
                if (menu != null) {
                    menus.add(menu);
                }
            }
            index++;
        }


        // 构造树结构
        RbacMenu rootMenu = null;
        for (RbacMenu i : menus) {
            if (i.getMenuParentId().equals(0)) {
                rootMenu = i;
                break;
            }
        }
        if (rootMenu == null) {
            throw new BusinessException("获取用户权限，未查询到根节点");
        }

        RbacMenuTree tree = new RbacMenuTree(rootMenu);
        tree = iRbacMenuService.getTreeByList(menus, tree);

        return tree;
    }

    @Override
    public boolean isInMenus(List<RbacMenu> menus, Integer menuId) {
        for (RbacMenu i : menus) {
            if (i.getMenuId().equals(menuId)) {
                return true;
            }
        }
        return false;
    }
}
