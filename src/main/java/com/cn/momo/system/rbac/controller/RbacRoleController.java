package com.cn.momo.system.rbac.controller;

import com.cn.momo.annotation.CallLog;
import com.cn.momo.annotation.Permission;
import com.cn.momo.system.rbac.pojo.RbacRole;
import com.cn.momo.system.rbac.service.IRbacRoleService;
import com.cn.momo.util.CheckUtil;
import com.cn.momo.util.ResultUtil;
import com.cn.momo.util.TransUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限角色
 * dongwenmo 2021-05-18
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/rbacRole")
public class RbacRoleController {
    @Autowired
    private IRbacRoleService iRbacRoleService;

    @Permission
    @PostMapping("/select")
    @CallLog(name = "select", desc = "根据实体中的属性值进行查询，查询条件使用等号")
    public String select(RbacRole rbacRole) {
        ResultUtil result = new ResultUtil();
        result.put("rbacRoles", iRbacRoleService.select(rbacRole));
        return result.success();
    }

    @Permission
    @PostMapping("/selectByPrimaryKey")
    @CallLog(name = "selectByPrimaryKey", desc = "根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号")
    public String selectByPrimaryKey(Integer key) throws Exception {
        ResultUtil result = new ResultUtil();
        CheckUtil.isNull(key, "主键");
        result.put("rbacRole", iRbacRoleService.selectByPrimaryKey(key));
        return result.success();
    }

    @Permission
    @PostMapping("/selectAll")
    @CallLog(name = "selectAll", desc = "查询全部结果")
    public String selectAll() {
        ResultUtil result = new ResultUtil();
        result.put("rbacRoles", iRbacRoleService.selectAll());
        return result.success();
    }

    @Permission
    @PostMapping("/selectOne")
    @CallLog(name = "selectOne", desc = "根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号")
    public String selectOne(RbacRole rbacRole) {
        ResultUtil result = new ResultUtil();
        result.put("rbacRole", iRbacRoleService.selectOne(rbacRole));
        return result.success();
    }

    @Permission
    @PostMapping("/selectCount")
    @CallLog(name = "selectCount", desc = "根据实体中的属性查询总数，查询条件使用等号")
    public String selectCount(RbacRole rbacRole) {
        ResultUtil result = new ResultUtil();
        result.put("rbacRoles", iRbacRoleService.selectCount(rbacRole));
        return result.success();
    }

    @Permission
    @PostMapping("/insert")
    @CallLog(name = "insert", desc = "保存一个实体，null的属性也会保存，不会使用数据库默认值")
    public String insert(RbacRole rbacRole) {
        ResultUtil result = new ResultUtil();
        iRbacRoleService.insert(rbacRole);
        return result.success("新增成功");
    }

    @Permission
    @PostMapping("/insertSelective")
    @CallLog(name = "insertSelective", desc = "保存一个实体，null的属性不会保存，会使用数据库默认值")
    public String insertSelective(RbacRole rbacRole) {
        ResultUtil result = new ResultUtil();
        iRbacRoleService.insertSelective(rbacRole);
        return result.success("新增成功");
    }

    @Permission
    @PostMapping("/updateByPrimaryKey")
    @CallLog(name = "updateByPrimaryKey", desc = "根据主键更新实体全部字段，null值会被更新")
    public String updateByPrimaryKey(RbacRole rbacRole) {
        ResultUtil result = new ResultUtil();
        iRbacRoleService.updateByPrimaryKey(rbacRole);
        return result.success("更新成功");
    }

    @Permission
    @PostMapping("/updateByPrimaryKeySelective")
    @CallLog(name = "updateByPrimaryKeySelective", desc = "根据主键更新属性不为null的值")
    public String updateByPrimaryKeySelective(RbacRole rbacRole) {
        ResultUtil result = new ResultUtil();
        iRbacRoleService.updateByPrimaryKeySelective(rbacRole);
        return result.success("更新成功");
    }

    @Permission
    @PostMapping("/delete")
    @CallLog(name = "delete", desc = "根据实体属性作为条件进行删除，查询条件使用等号")
    public String delete(RbacRole rbacRole) {
        ResultUtil result = new ResultUtil();
        iRbacRoleService.delete(rbacRole);
        return result.success("删除成功");
    }

    @Permission
    @PostMapping("/deleteByPrimaryKey")
    @CallLog(name = "deleteByPrimaryKey", desc = "根据主键字段进行删除，方法参数必须包含完整的主键属性")
    public String deleteByPrimaryKey(Integer key) throws Exception {
        ResultUtil result = new ResultUtil();
        CheckUtil.isNull(key, "主键");
        iRbacRoleService.deleteByPrimaryKey(key);
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
            count += iRbacRoleService.deleteByPrimaryKey(i);
        }
        result.put("count", count);
        return result.success("删除成功");
    }

}
