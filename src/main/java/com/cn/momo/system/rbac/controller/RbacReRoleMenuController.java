package com.cn.momo.system.rbac.controller;

import com.cn.momo.annotation.CallLog;
import com.cn.momo.annotation.Permission;
import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.rbac.pojo.RbacReRoleMenu;
import com.cn.momo.system.rbac.service.IRbacReRoleMenuService;
import com.cn.momo.system.user.pojo.User;
import com.cn.momo.util.CheckUtil;
import com.cn.momo.util.ResultUtil;
import com.cn.momo.util.StringUtil;
import com.cn.momo.util.TransUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * auto 2021-01-25 18:56:10
 */
@Controller
@CrossOrigin("*")
@RequestMapping("/rbacReRoleMenu")
public class RbacReRoleMenuController {
    private static Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    @Autowired
    private IRbacReRoleMenuService iRbacReRoleMenuService;

    @PostMapping("/select")
    @ResponseBody
    @CallLog(name = "select", desc = "根据实体中的属性值进行查询，查询条件使用等号")
    public String select(User user, RbacReRoleMenu rbacReRoleMenu) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (rbacReRoleMenu == null) {
                throw new BusinessException("查询条件不能为空");
            }
            List<RbacReRoleMenu> rbacReRoleMenus = iRbacReRoleMenuService.select(rbacReRoleMenu);
            map.put("rbacReRoleMenus", rbacReRoleMenus);
            map.put("flag", 0);
            map.put("msg", "");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @PostMapping("/selectByPrimaryKey")
    @ResponseBody
    @CallLog(name = "selectByPrimaryKey", desc = "根据主键字段进行查询，方法参数必须包含完整的主键属性，查询条件使用等号")
    public String selectByPrimaryKey(User user, Integer key) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (key == null) {
                throw new BusinessException("字段key不能为空");
            }
            RbacReRoleMenu rbacReRoleMenu = iRbacReRoleMenuService.selectByPrimaryKey(key);
            map.put("rbacReRoleMenu", rbacReRoleMenu);
            map.put("flag", 0);
            map.put("msg", "");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @PostMapping("/selectAll")
    @ResponseBody
    @CallLog(name = "selectAll", desc = "查询全部结果")
    public String selectAll(User user) {
        HashMap<String, Object> map = new HashMap<>();
        List<RbacReRoleMenu> rbacReRoleMenus = iRbacReRoleMenuService.selectAll();
        map.put("rbacReRoleMenus", rbacReRoleMenus);
        map.put("flag", 0);
        map.put("msg", "");
        return gson.toJson(map);
    }

    @PostMapping("/selectOne")
    @ResponseBody
    @CallLog(name = "selectOne", desc = "根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号")
    public String selectOne(User user, RbacReRoleMenu rbacReRoleMenu) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (rbacReRoleMenu == null) {
                throw new BusinessException("查询条件不能为空");
            }
            rbacReRoleMenu = iRbacReRoleMenuService.selectOne(rbacReRoleMenu);
            map.put("rbacReRoleMenu", rbacReRoleMenu);
            map.put("flag", 0);
            map.put("msg", "");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @PostMapping("/selectCount")
    @ResponseBody
    @CallLog(name = "selectCount", desc = "根据实体中的属性查询总数，查询条件使用等号")
    public String selectCount(User user, RbacReRoleMenu rbacReRoleMenu) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (rbacReRoleMenu == null) {
                throw new BusinessException("查询条件不能为空");
            }
            int count = iRbacReRoleMenuService.selectCount(rbacReRoleMenu);
            map.put("count", count);
            map.put("flag", 0);
            map.put("msg", "");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @PostMapping("/insert")
    @ResponseBody
    @CallLog(name = "insert", desc = "保存一个实体，null的属性也会保存，不会使用数据库默认值")
    public String insert(User user, RbacReRoleMenu rbacReRoleMenu) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (rbacReRoleMenu == null) {
                throw new BusinessException("新增对象不能为空");
            }
            iRbacReRoleMenuService.insert(rbacReRoleMenu);
            map.put("rbacReRoleMenu", rbacReRoleMenu);
            map.put("flag", 0);
            map.put("msg", "新增成功");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @PostMapping("/insertSelective")
    @ResponseBody
    @CallLog(name = "insertSelective", desc = "保存一个实体，null的属性不会保存，会使用数据库默认值")
    public String insertSelective(User user, RbacReRoleMenu rbacReRoleMenu) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (rbacReRoleMenu == null) {
                throw new BusinessException("新增对象不能为空");
            }
            iRbacReRoleMenuService.insertSelective(rbacReRoleMenu);
            map.put("rbacReRoleMenu", rbacReRoleMenu);
            map.put("flag", 0);
            map.put("msg", "新增成功");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @PostMapping("/updateByPrimaryKey")
    @ResponseBody
    @CallLog(name = "updateByPrimaryKey", desc = "根据主键更新实体全部字段，null值会被更新")
    public String updateByPrimaryKey(User user, RbacReRoleMenu rbacReRoleMenu) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (rbacReRoleMenu == null) {
                throw new BusinessException("更新对象不能为空");
            }
            int count = iRbacReRoleMenuService.updateByPrimaryKey(rbacReRoleMenu);
            map.put("count", count);
            map.put("flag", 0);
            map.put("msg", "更新成功");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @PostMapping("/updateByPrimaryKeySelective")
    @ResponseBody
    @CallLog(name = "updateByPrimaryKeySelective", desc = "根据主键更新属性不为null的值")
    public String updateByPrimaryKeySelective(User user, RbacReRoleMenu rbacReRoleMenu) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (rbacReRoleMenu == null) {
                throw new BusinessException("更新对象不能为空");
            }
            int count = iRbacReRoleMenuService.updateByPrimaryKeySelective(rbacReRoleMenu);
            map.put("count", count);
            map.put("flag", 0);
            map.put("msg", "更新成功");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @PostMapping("/delete")
    @ResponseBody
    @CallLog(name = "delete", desc = "根据实体属性作为条件进行删除，查询条件使用等号")
    public String delete(User user, RbacReRoleMenu rbacReRoleMenu) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (rbacReRoleMenu == null) {
                throw new BusinessException("删除条件不能为空");
            }
            int count = iRbacReRoleMenuService.delete(rbacReRoleMenu);
            map.put("count", count);
            map.put("flag", 0);
            map.put("msg", "删除成功");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @PostMapping("/deleteByPrimaryKey")
    @ResponseBody
    @CallLog(name = "deleteByPrimaryKey", desc = "根据主键字段进行删除，方法参数必须包含完整的主键属性")
    public String deleteByPrimaryKey(User user, Integer key) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (key == null) {
                throw new BusinessException("主键不能为空");
            }
            int count = iRbacReRoleMenuService.deleteByPrimaryKey(key);
            map.put("count", count);
            map.put("flag", 0);
            map.put("msg", "删除成功");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @PostMapping("/deleteByPrimaryKeys")
    @ResponseBody
    @CallLog(name = "deleteByPrimaryKeys", desc = "根据主键字段进行删除，方法参数必须包含完整的主键属性（批量删除）")
    public String deleteByPrimaryKeys(User user, String keys) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (StringUtil.isNull(keys)) {
                throw new BusinessException("主键不能为空");
            }
            int[] key = TransUtil.string2ints(keys);
            int count = 0;
            for (int i : key) {
                count += iRbacReRoleMenuService.deleteByPrimaryKey(i);
            }
            map.put("count", count);
            map.put("flag", 0);
            map.put("msg", "删除成功");
        } catch (BusinessException e) {
            e.printStackTrace();
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @Permission
    @PostMapping("/switchMenuAuthority")
    @ResponseBody
    @CallLog(name = "switchMenuAuthority", desc = "变更菜单权限")
    public String switchMenuAuthority(RbacReRoleMenu rbacReRoleMenu) throws Exception {
        ResultUtil result = new ResultUtil();
        CheckUtil.isNull(rbacReRoleMenu.getRoleId(), "角色id");
        CheckUtil.isNull(rbacReRoleMenu.getMenuId(), "菜单id");
        CheckUtil.isIn(rbacReRoleMenu.getType(), "权限类型", "1", "2");// 1-增加 2-取消
        iRbacReRoleMenuService.switchMenuAuthority(rbacReRoleMenu);
        return result.success("变更成功");
    }

    @PostMapping("/getMenuAuthority")
    @ResponseBody
    @CallLog(name = "getMenuAuthority", desc = "获取菜单权限")
    public String getMenuAuthority(Integer roleId) throws Exception {
        ResultUtil result = new ResultUtil();
        CheckUtil.isNull(roleId, "角色id");
        result.put("menuIds", iRbacReRoleMenuService.getMenuAuthority(roleId));
        return result.success();
    }

}
