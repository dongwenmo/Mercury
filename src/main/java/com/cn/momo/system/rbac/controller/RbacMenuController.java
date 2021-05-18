package com.cn.momo.system.rbac.controller;

import com.cn.momo.annotation.CallLog;
import com.cn.momo.annotation.Permission;
import com.cn.momo.system.rbac.dto.MoveMenuDTO;
import com.cn.momo.system.rbac.pojo.RbacMenu;
import com.cn.momo.system.rbac.pojo.RbacMenuTree;
import com.cn.momo.system.rbac.service.IRbacMenuService;
import com.cn.momo.util.CheckUtil;
import com.cn.momo.util.ResultUtil;
import com.cn.momo.util.TransUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限菜单
 * dongwenmo 2021-05-18
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/rbacMenu")
public class RbacMenuController {
    @Autowired
    private IRbacMenuService iRbacMenuService;

    @Permission
    @PostMapping("/select")
    @CallLog(name = "select", desc = "根据实体中的属性值进行查询，查询条件使用等号")
    public String select(RbacMenu rbacMenu) {
        ResultUtil result = new ResultUtil();
        result.put("rbacMenus", iRbacMenuService.select(rbacMenu));
        return result.success();
    }

    @Permission
    @PostMapping("/selectByPrimaryKey")
    @CallLog(name = "selectByPrimaryKey", desc = "根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号")
    public String selectByPrimaryKey(Integer key) throws Exception {
        ResultUtil result = new ResultUtil();
        CheckUtil.isNull(key, "主键");
        result.put("rbacMenus", iRbacMenuService.selectByPrimaryKey(key));
        return result.success();
    }

    @Permission
    @PostMapping("/selectAll")
    @CallLog(name = "selectAll", desc = "查询全部结果")
    public String selectAll() {
        ResultUtil result = new ResultUtil();
        result.put("rbacMenus", iRbacMenuService.selectAll());
        return result.success();
    }

    @Permission
    @PostMapping("/selectOne")
    @CallLog(name = "selectOne", desc = "根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号")
    public String selectOne(RbacMenu rbacMenu) {
        ResultUtil result = new ResultUtil();
        result.put("rbacMenu", iRbacMenuService.selectOne(rbacMenu));
        return result.success();
    }

    @Permission
    @PostMapping("/selectCount")
    @CallLog(name = "selectCount", desc = "根据实体中的属性查询总数，查询条件使用等号")
    public String selectCount(RbacMenu rbacMenu) {
        ResultUtil result = new ResultUtil();
        result.put("rbacMenu", iRbacMenuService.selectCount(rbacMenu));
        return result.success();
    }

    @Permission
    @PostMapping("/insert")
    @CallLog(name = "insert", desc = "保存一个实体，null的属性也会保存，不会使用数据库默认值")
    public String insert(RbacMenu rbacMenu) {
        ResultUtil result = new ResultUtil();
        iRbacMenuService.insert(rbacMenu);
        return result.success("新增成功");
    }

    @Permission
    @PostMapping("/insertSelective")
    @CallLog(name = "insertSelective", desc = "保存一个实体，null的属性不会保存，会使用数据库默认值")
    public String insertSelective(RbacMenu rbacMenu) {
        ResultUtil result = new ResultUtil();
        iRbacMenuService.insertSelective(rbacMenu);
        return result.success("新增成功");
    }

    @Permission
    @PostMapping("/updateByPrimaryKey")
    @CallLog(name = "updateByPrimaryKey", desc = "根据主键更新实体全部字段，null值会被更新")
    public String updateByPrimaryKey(RbacMenu rbacMenu) {
        ResultUtil result = new ResultUtil();
        iRbacMenuService.updateByPrimaryKey(rbacMenu);
        return result.success("更新成功");
    }

    @Permission
    @PostMapping("/updateByPrimaryKeySelective")
    @CallLog(name = "updateByPrimaryKeySelective", desc = "根据主键更新属性不为null的值")
    public String updateByPrimaryKeySelective(RbacMenu rbacMenu) {
        ResultUtil result = new ResultUtil();
        iRbacMenuService.updateByPrimaryKeySelective(rbacMenu);
        return result.success("更新成功");
    }

    @Permission
    @PostMapping("/delete")
    @CallLog(name = "delete", desc = "根据实体属性作为条件进行删除，查询条件使用等号")
    public String delete(RbacMenu rbacMenu) {
        ResultUtil result = new ResultUtil();
        iRbacMenuService.delete(rbacMenu);
        return result.success("删除成功");
    }

    @Permission
    @PostMapping("/deleteByPrimaryKey")
    @CallLog(name = "deleteByPrimaryKey", desc = "根据主键字段进行删除，方法参数必须包含完整的主键属性")
    public String deleteByPrimaryKey(Integer key) {
        ResultUtil result = new ResultUtil();
        iRbacMenuService.deleteByPrimaryKey(key);
        return result.success("删除成功");
    }

    @Permission
    @PostMapping("/deleteByPrimaryKeys")
    @CallLog(name = "deleteByPrimaryKeys", desc = "根据主键字段进行删除，方法参数必须包含完整的主键属性（批量删除）")
    public String deleteByPrimaryKeys(String keys) throws Exception {
        ResultUtil result = new ResultUtil();
        CheckUtil.isNull(keys, "主键");
        int[] key = TransUtil.string2ints(keys);
        int count = 0;
        for (int i : key) {
            count += iRbacMenuService.deleteByPrimaryKey(i);
        }
        result.put("count", count);
        return result.success("删除成功");
    }

    @Permission
    @PostMapping("/getTreeById")
    @CallLog(name = "getTreeById", desc = "通过菜单id获取权限菜单树")
    public String getTreeById(Integer key) throws Exception {
        ResultUtil result = new ResultUtil();
        CheckUtil.isNull(key, "菜单id");
        RbacMenuTree tree = new RbacMenuTree();
        tree.setMenuId(key);
        tree = new RbacMenuTree(iRbacMenuService.selectByPrimaryKey(tree.getMenuId()));
        tree = iRbacMenuService.getTreeById(tree);
        result.put("tree", tree);
        return result.success();
    }

    @Permission
    @PostMapping("/moveMenuNode")
    @CallLog(name = "moveMenuNode", desc = "权限菜单-移动菜单节点")
    public String moveMenuNode(MoveMenuDTO moveMenuDTO) throws Exception {
        ResultUtil result = new ResultUtil();
        CheckUtil.isNull(moveMenuDTO.getMenuId(), "移动的菜单id");
        CheckUtil.isNull(moveMenuDTO.getMenuAimId(), "目标位置菜单id");
        iRbacMenuService.moveMenuNode(moveMenuDTO);
        return result.success();
    }

}
