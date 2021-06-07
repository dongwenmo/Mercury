package com.cn.momo.system.rbac.service.impl;

import com.cn.momo.common.BaseServiceImpl;
import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.rbac.pojo.Menu;
import com.cn.momo.system.rbac.service.IMenuService;
import com.cn.momo.util.sql.SQL;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author dongwenmo
 * @create 2020-08-02 10:48
 */
@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu> implements IMenuService {
    @Override
    public List<Menu> selectMenuOrderByPriority(int menuParentId) throws BusinessException {
        SQL sql = new SQL();
        List<Menu> menus = new ArrayList<>();

        sql.clear();
        sql.addSql(" select *                   ");
        sql.addSql("   from menu                ");
        sql.addSql("  where menu_parent_id = ?  ");
        sql.addSql("  order by priority         ");
        sql.setPara(menuParentId);
        List<Map<String, Object>> requestList = sql.query();

        for (int i = 0; i < requestList.size(); i++) {
            Menu menu = new Menu();
            Map<String, Object> map = requestList.get(i);
            menu.setMenuId((Integer) map.get("menu_id"));
            menu.setMenuParentId((Integer) map.get("menu_parent_id"));
            menu.setMenuName((String) map.get("menu_name"));
            menu.setMenuType((String) map.get("menu_type"));
            menu.setUrl((String) map.get("url"));
            menu.setMenuDesc((String) map.get("menu_desc"));
            menu.setPriority((Integer) map.get("priority"));

            menus.add(menu);
        }
        return menus;
    }

    @Override
    public int moveUpMenu(Menu menu) {
        Menu curMenu = this.selectByPrimaryKey(menu.getMenuId());
        int priority = curMenu.getPriority();

        // 获取上一个菜单信息
        Menu prevMenu = new Menu();
        prevMenu.setMenuParentId(curMenu.getMenuParentId());
        prevMenu.setPriority(priority - 1);
        prevMenu = this.selectOne(prevMenu);

        if (prevMenu == null) {
            return 0;
        }

        // 更新序号
        prevMenu.setPriority(priority);
        this.updateByPrimaryKeySelective(prevMenu);
        curMenu.setPriority(priority - 1);
        this.updateByPrimaryKeySelective(curMenu);

        return 1;
    }

    @Override
    public int moveDownMenu(Menu menu) {
        Menu curMenu = this.selectByPrimaryKey(menu.getMenuId());
        int priority = curMenu.getPriority();

        // 获取下一个菜单信息
        Menu nextMenu = new Menu();
        nextMenu.setMenuParentId(curMenu.getMenuParentId());
        nextMenu.setPriority(priority + 1);
        nextMenu = this.selectOne(nextMenu);

        if (nextMenu == null) {
            return 0;
        }

        // 更新序号
        nextMenu.setPriority(priority);
        this.updateByPrimaryKeySelective(nextMenu);
        curMenu.setPriority(priority + 1);
        this.updateByPrimaryKeySelective(curMenu);
        return 1;
    }

    @Override
    public int deleteMenu(int menuId) throws BusinessException {
        // 获取当前菜单
        Menu curMenu = this.selectByPrimaryKey(menuId);

        // 删除该菜单
        this.deleteByPrimaryKey(menuId);

        // 获取该菜单的同级菜单
        List<Menu> menus = this.selectMenuOrderByPriority(curMenu.getMenuParentId());

        int priority = 1;
        for (Menu i : menus) {
            i.setPriority(priority++);
            this.updateByPrimaryKeySelective(i);
        }

        return 1;
    }
}
