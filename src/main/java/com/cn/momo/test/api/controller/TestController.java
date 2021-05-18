package com.cn.momo.test.api.controller;

import com.cn.momo.annotation.CallLog;
import com.cn.momo.annotation.Permission;
import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.user.cache.UserCache;
import com.cn.momo.system.user.dto.UserCacheDTO;
import com.cn.momo.system.user.pojo.User;
import com.cn.momo.system.user.service.IUserService;
import com.cn.momo.util.CheckUtil;
import com.cn.momo.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * dongwenmo 2021-05-16
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/test")
public class TestController {
    @Autowired
    private IUserService iUserService;

    @GetMapping("/test")
    @Permission
    @CallLog(name = "test", desc = "测试接口")
    public String test(String token) throws Exception {
        ResultUtil result = new ResultUtil();
        UserCacheDTO user = UserCache.get(token);
        result.put("userId", 1);
        result.put("user", user);
        result.put("users", UserCache.getAll());

        return result.success();
    }

    @GetMapping("/rbac")
    @Permission
    @CallLog(name = "rbac", desc = "测试权限")
    public String rbac(String token) throws Exception {
        ResultUtil result = new ResultUtil();
        UserCacheDTO user = UserCache.get(token);
        result.put("userId", 1);
        result.put("user", user);

        return result.success();
    }
}
