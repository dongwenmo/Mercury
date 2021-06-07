package com.cn.momo.system.user.controller;

import com.cn.momo.annotation.CallLog;
import com.cn.momo.annotation.Permission;
import com.cn.momo.common.SupperController;
import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.user.dto.LoginUserDTO;
import com.cn.momo.system.user.pojo.User;
import com.cn.momo.system.user.service.IUserService;
import com.cn.momo.util.CheckUtil;
import com.cn.momo.util.HttpUtil;
import com.cn.momo.util.JsonUtil;
import com.cn.momo.util.ResultUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * dongwenmo 2020-04-23
 */
@Controller
@CrossOrigin("*")
@RequestMapping("/login")
public class LoginController extends SupperController {
    @Autowired
    private Gson gson;
    @Autowired
    private IUserService iUserService;

    @GetMapping("/test")
    @ResponseBody
    @Permission
    @CallLog(name = "test", desc = "测试接口")
    public String test() {
        Map<String, Object> map = new HashMap<>();

        
        
        gson = new Gson().newBuilder().serializeNulls().create();
        map.put("curUser", this.getCurentUser().getToken());
        return gson.toJson(map);
    }

    @PostMapping("/login")
    @ResponseBody
    @CallLog(name = "login", desc = "用户登录")
    public String login(User user, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            String ip = HttpUtil.getUserIP(request);
            user.setLastIp(ip);
            map.put("user", iUserService.doLogin(user));
            map.put("flag", 0);
            map.put("msg", "登录成功");
        } catch (BusinessException e) {
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return JsonUtil.toJson(map);
    }

    @PostMapping("/loginForToken")
    @ResponseBody
    @CallLog(name = "loginForToken", desc = "用户登录生成token")
    public String loginForToken(LoginUserDTO user) throws Exception {
        ResultUtil request = new ResultUtil();
        user.setIp(HttpUtil.getIP());
        request.put("token", iUserService.loginForToken(user));
        return request.success();
    }

    @PostMapping("/register")
    @ResponseBody
    @CallLog(name = "register", desc = "用户注册")
    public String register(User user, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            String ip = HttpUtil.getUserIP(request);
            user.setLastIp(ip);
            map.put("user", iUserService.register(user));
            map.put("flag", 0);
            map.put("msg", "注册成功");
        } catch (BusinessException e) {
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return gson.toJson(map);
    }

    @GetMapping("/checkUniqueUsername/{username}")
    @ResponseBody
    @CallLog(name = "checkUniqueUsername", desc = "判断用户名是否存在")
    public String checkUsernameExist(User user, @PathVariable("username") String username) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            CheckUtil.isNull(username, "用户名");
            User iuser = new User();
            iuser.setUsername(username);
            if (iUserService.checkUsernameExist(iuser)) {
                throw new BusinessException("该用户名已存在");
            }
            map.put("flag", 0);
            map.put("msg", "");
        } catch (BusinessException e) {
            map.put("flag", -1);
            map.put("msg", e.getMessage());
        }
        return JsonUtil.toJson(map);
    }

    /**
     * @param username:    用户名
     * @param mailAccount: 验证邮箱
     * @desc: 通过用户名、邮箱地址发送验证码（忘记密码）
     * @author: dongwenmo
     * @create: 2020-08-13 12:03:24
     * @return: java.lang.String
     */
    @PostMapping("/sendCodeToMail")
    @ResponseBody
    @CallLog(name = "sendCodeToMail", desc = "通过用户名、邮箱地址发送验证码（忘记密码）")
    public String sendCodeToMail(User user, String username, String mailAccount) throws BusinessException {
        HashMap<String, Object> map = iUserService.sendCodeToMail(username, mailAccount);
        return gson.toJson(map);
    }

    /**
     * @param username:    用户名
     * @param code:        验证码
     * @param newPassword: 新密码
     * @desc: 通过用户名、邮箱验证码修改密码（忘记密码）
     * @author: dongwenmo
     * @create: 2020-08-13 12:04:16
     * @return: java.lang.String
     */
    @PostMapping("/modifyUserPasswordByMail")
    @ResponseBody
    @CallLog(name = "modifyUserPasswordByMail", desc = "通过用户名、邮箱验证码修改密码（忘记密码）")
    public String modifyUserPasswordByMail(User user, String username, String code, String newPassword) throws BusinessException {
        HashMap<String, Object> map = iUserService.modifyUserPasswordByMail(username, code, newPassword);
        return gson.toJson(map);
    }

    /**
     * @param username:    用户名
     * @param oldPassword: 旧密码
     * @param newPassword: 新密码
     * @desc: 通过用户名、原密码修改密码（修改密码）
     * @author: dongwenmo
     * @create: 2020-08-13 12:05:17
     * @return: java.lang.String
     */
    @PostMapping("/modifyUserPasswordByOldPassword")
    @ResponseBody
    @CallLog(name = "modifyUserPasswordByOldPassword", desc = "通过用户名、原密码修改密码（修改密码）")
    public String modifyUserPasswordByOldPassword(User user, String username, String oldPassword, String newPassword) throws BusinessException {
        HashMap<String, Object> map = iUserService.modifyUserPasswordByOldPassword(username, oldPassword, newPassword);
        return gson.toJson(map);
    }

}
