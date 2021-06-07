package com.cn.momo.system.rbac.controller;

import com.cn.momo.annotation.CallLog;
import com.cn.momo.annotation.Permission;
import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.rbac.service.IRbacService;
import com.cn.momo.system.user.service.IUserService;
import com.cn.momo.util.CheckUtil;
import com.cn.momo.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限系统
 * dongwenmo 2021-01-25
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/rbac")
public class RbacController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IRbacService iRbacService;

    @Permission
    @PostMapping("/queryUserInfo")
    @CallLog(name = "queryUserInfo", desc = "查询用户信息")
    public String queryUserInfo() throws BusinessException {
        ResultUtil result = new ResultUtil();
        List<Map<String, Object>> resultList = iRbacService.queryUserInfo(new HashMap<>());
        result.put("userInfo", resultList);
        return result.success();
    }

    @Permission
    @PostMapping("/getUserMenu")
    @CallLog(name = "getUserMenu", desc = "获取用户拥有权限的菜单")
    public String getUserMenu() throws Exception {
        ResultUtil result = new ResultUtil();
        Integer userId = iUserService.getUser().getUserId();
        CheckUtil.isNull(userId, "用户id");
        result.put("menuTree", iRbacService.getUserMenu(userId));
        return result.success();
    }

}
