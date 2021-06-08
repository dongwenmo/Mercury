package com.cn.momo.system.user.controller;

import com.cn.momo.annotation.CallLog;
import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.user.dto.LoginUserDTO;
import com.cn.momo.system.user.pojo.User;
import com.cn.momo.system.user.service.IUserService;
import com.cn.momo.util.CheckUtil;
import com.cn.momo.util.HttpUtil;
import com.cn.momo.util.ResultUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * dongwenmo 2020-04-23
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private Gson gson;
    @Autowired
    private IUserService iUserService;

    @PostMapping("/loginForToken")
    @CallLog(name = "loginForToken", desc = "用户登录生成token")
    public String loginForToken(LoginUserDTO user) throws Exception {
        ResultUtil result = new ResultUtil();
        user.setIp(HttpUtil.getIP());
        result.put("token", iUserService.loginForToken(user));
        return result.success();
    }

    @PostMapping("/register")
    @CallLog(name = "register", desc = "用户注册")
    public String register(User user) throws BusinessException {
        ResultUtil result = new ResultUtil();
        user.setLastIp(HttpUtil.getIP());
        result.put("token", iUserService.register(user));
        return result.success("注册成功");
    }

    @GetMapping("/checkUniqueUsername/{username}")
    @CallLog(name = "checkUniqueUsername", desc = "判断用户名是否存在")
    public String checkUsernameExist(@PathVariable("username") String username) throws BusinessException {
        ResultUtil result = new ResultUtil();
        CheckUtil.isNull(username, "用户名");
        iUserService.checkUsernameExist(username);
        return result.success();
    }

    @PostMapping("/sendCodeToMail")
    @CallLog(name = "sendCodeToMail", desc = "通过用户名、邮箱地址发送验证码（忘记密码）")
    public String sendCodeToMail(String username, String mailAccount) throws BusinessException {
        HashMap<String, Object> map = iUserService.sendCodeToMail(username, mailAccount);
        return gson.toJson(map);
    }

    @PostMapping("/modifyUserPasswordByMail")
    @CallLog(name = "modifyUserPasswordByMail", desc = "通过用户名、邮箱验证码修改密码（忘记密码）")
    public String modifyUserPasswordByMail(String username, String code, String newPassword) throws BusinessException {
        HashMap<String, Object> map = iUserService.modifyUserPasswordByMail(username, code, newPassword);
        return gson.toJson(map);
    }

    @PostMapping("/modifyUserPasswordByOldPassword")
    @CallLog(name = "modifyUserPasswordByOldPassword", desc = "通过用户名、原密码修改密码（修改密码）")
    public String modifyUserPasswordByOldPassword(String username, String oldPassword, String newPassword) throws BusinessException {
        HashMap<String, Object> map = iUserService.modifyUserPasswordByOldPassword(username, oldPassword, newPassword);
        return gson.toJson(map);
    }
}
