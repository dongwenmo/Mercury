package com.cn.momo.system.user.service.impl;

import com.cn.momo.config.ErrorConfig;
import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.user.cache.UserCache;
import com.cn.momo.system.user.dto.LoginUserDTO;
import com.cn.momo.system.user.dto.UserCacheDTO;
import com.cn.momo.system.user.mapper.UserLoginMapper;
import com.cn.momo.system.user.mapper.UserMapper;
import com.cn.momo.system.user.pojo.User;
import com.cn.momo.system.user.pojo.UserLogin;
import com.cn.momo.system.user.service.IUserService;
import com.cn.momo.util.*;
import com.cn.momo.util.sql.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * dongwenmo  2020-04-15
 */
@Service
public class UserServiceImpl implements IUserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserLoginMapper userLoginMapper;


    @Override
    public String getAccessToken() throws BusinessException {
        String accessToken;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            accessToken = request.getHeader("accessToken");
            if (StringUtil.isNull(accessToken)) {
                throw new BusinessException();
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorConfig.ERR_10001, "accessToken不存在");
        }
        return accessToken;
    }

    @Override
    public UserCacheDTO getUser() throws BusinessException {
        UserCacheDTO user = UserCache.get(getAccessToken());
        if (user == null) {
            throw new BusinessException(ErrorConfig.ERR_10002, "获取用户信息失败");
        }

        return user;
    }

    @Override
    public User selectByPrimaryKey(int userid) {
        return userMapper.selectByPrimaryKey(userid);
    }

    @Override
    public User selectOne(User user) throws BusinessException {
        try {
            return userMapper.selectOne(user);
        } catch (Exception e) {
            throw new BusinessException("查询结果不唯一");
        }
    }

    @Override
    public User doLogin(User user) throws BusinessException {
        String ip = user.getLastIp();
        CheckUtil.isNull(user.getUsername(), "用户名");
        CheckUtil.isNull(user.getPassword(), "密码");
        CheckUtil.isNull(ip, "IP地址");

        String password = MD5Util.getMD5(user.getPassword(), user.getUsername());

        // 通过用户名查询该用户
        User loginUser = new User();
        loginUser.setUsername(user.getUsername());
        User vUser = this.selectOne(loginUser);

        // 检验登录密码
        if (vUser != null) {
            if (password.equals(vUser.getPassword())) {
                // 登录成功
                // 记录一次登录时间
                UserLogin userLogin = new UserLogin();
                userLogin.setUserId(vUser.getUserId());
                userLogin.setLoginTime(new Date());
                userLogin.setLoginIp(ip);
                userLogin.setFlag("0");
                userLoginMapper.insert(userLogin);
                // 更新用户信息
                // 生成新的token
                vUser.setToken(StringUtil.getUUID());
                vUser.setLastIp(ip);
                userMapper.updateByPrimaryKey(vUser);

                return vUser;
            } else {
                // 密码错误
                // 记录一次登录失败时间
                UserLogin userLogin = new UserLogin();
                userLogin.setUserId(vUser.getUserId());
                userLogin.setLoginTime(new Date());
                userLogin.setLoginIp(ip);
                userLogin.setFlag("1");
                userLoginMapper.insert(userLogin);
                throw new BusinessException(ErrorConfig.ERR_10005, "密码错误");
            }
        } else {
            throw new BusinessException(ErrorConfig.ERR_10006, "该用户不存在");
        }
    }

    @Override
    public String loginForToken(LoginUserDTO loginUserDTO) throws BusinessException {
        User vUser = new User();
        vUser.setUsername(loginUserDTO.getUsername());
        vUser.setPassword(loginUserDTO.getPassword());
        vUser.setLastIp(loginUserDTO.getIp());
        User user = doLogin(vUser);

        String token = user.getToken();
        UserCacheDTO userCacheDTO = new UserCacheDTO(user);
        userCacheDTO.setAppId(loginUserDTO.getAppId());

        // 将用户信息存入缓存
        UserCache.remove(userCacheDTO);
        UserCache.put(token, userCacheDTO);

        return token;
    }

    @Override
    public boolean checkUsernameExist(User user) {
        int userCount = userMapper.selectCount(user);
        return userCount > 0;
    }

    @Override
    public User register(User user) throws BusinessException {
        String password = user.getPassword();
        CheckUtil.isNull(user.getUsername(), "用户名");
        CheckUtil.isNull(password, "密码");
        CheckUtil.isNull(user.getLastIp(), "IP地址");
        if (checkUsernameExist(user)) {
            throw new BusinessException(ErrorConfig.ERR_10007, "该用户已存在");
        }

        user.setCreateTime(new Date());
        user.setPassword(MD5Util.getMD5(password, user.getUsername()));
        int count = insert(user);
        if (count == 1) {
            user.setPassword(password);
            return doLogin(user);
        } else {
            throw new BusinessException(ErrorConfig.ERR_12001, "注册失败，请重试");
        }
    }

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public HashMap<String, Object> sendCodeToMail(String username, String mailAccount) throws BusinessException {
        HashMap<String, Object> map = new HashMap<>();

        // 判断用户名、邮箱地址是否与数据库一致
        SQL sql = new SQL();

        sql.clear();
        sql.addSql("  select user_id,username,identity_id  ");
        sql.addSql("    from user where username = ?     ");
        sql.setPara(username);
        List<Map<String, Object>> requestList = sql.query();

        if (requestList.size() == 0) {
            map.put("msg", "不存在该用户！");
            map.put("flag", "1");
            return map;
        }

        Integer identityid = (Integer) requestList.get(0).get("identity_id");

        if (identityid == null || identityid == 0) {
            map.put("msg", "对不起，您未实名认证，请联系系统管理员修改密码！");
            map.put("flag", "1");
            return map;
        }

        sql.clear();
        sql.addSql("  select identity_id, email   ");
        sql.addSql("    from user_identity        ");
        sql.addSql("   where identity_id = ?      ");

        sql.setPara(identityid);
        List<Map<String, Object>> identityList = sql.query();

        if (identityList.size() == 0) {
            map.put("msg", "未获取到该用户实名认证信息！");
            map.put("flag", "1");
            return map;
        }

        String email = (String) identityList.get(0).get("email");

        if (StringUtil.isNull(email)) {
            map.put("msg", "对不起，您未维护邮箱地址！");
            map.put("flag", "1");
            return map;
        }

        // 生成6位验证码
        String code = RandomUtil.generateCode(6);
        Date effectiveTime = DateUtil.addMinute(new Date(), 5);

        // 将验证码存入数据库
        sql.clear();
        sql.addSql("  update user_identity                 ");
        sql.addSql("     set code = ?, effective_time = ?  ");
        sql.addSql("   where identity_id = ?               ");

        sql.setPara(code, effectiveTime, identityid);
        sql.update();

        // 发送邮件
        String subject = "unicorn修改密码验证码：" + code;
        String content = "感谢您使用unicorn。您的验证码为：" + code;
        try {
            MailUtil.sendMail(email, subject, content);
        } catch (Exception e) {
            e.printStackTrace();
        }

        map.put("msg", "发送邮件成功！");
        map.put("flag", "0");
        return map;
    }

    @Override
    public HashMap<String, Object> modifyUserPasswordByMail(String username, String code, String newPassword) throws BusinessException {
        HashMap<String, Object> map = new HashMap<>();

        // 判断用户名与验证码是否与数据库一致
        SQL sql = new SQL();

        sql.clear();
        sql.addSql("  select a.userid,a.username,b.email,b.code, b.effective_time  ");
        sql.addSql("    from user a,                             ");
        sql.addSql("         user_identity b                     ");
        sql.addSql("   where a.identityid = b.identity_id        ");
        sql.addSql("     and a.username = ?                      ");
        sql.addSql("     and b.code = ?                          ");

        sql.setPara(username, code);
        List<Map<String, Object>> requestList = sql.query();

        if (requestList.size() == 0) {
            map.put("msg", "验证码错误！");
            map.put("flag", "1");
            return map;
        }

        Date effectiveTime = (Date) requestList.get(0).get("effective_time");
        if (effectiveTime.before(new Date())) {
            map.put("msg", "验证码有效时间已过，请重新发送验证码！");
            map.put("flag", "1");
            return map;
        }

        // 修改新密码
        String password = MD5Util.getMD5(newPassword, username);

        sql.clear();
        sql.addSql("  update user set password = ?  ");
        sql.addSql("   where username = ?           ");
        sql.setPara(password, username);
        int count = sql.update();

        if (count == 0) {
            map.put("msg", "密码修改失败，请重试！");
            map.put("flag", "1");
            return map;
        }

        map.put("msg", "密码修改成功！");
        map.put("flag", "0");
        return map;
    }

    @Override
    public HashMap<String, Object> modifyUserPasswordByOldPassword(String username, String oldPassword, String newPassword) throws BusinessException {
        HashMap<String, Object> map = new HashMap<>();

        // 判断用户名与旧密码是否与数据库一致
        SQL sql = new SQL();
        sql.clear();
        sql.addSql("  select userid,username from user  ");
        sql.addSql("   where username = ?               ");
        sql.addSql("     and password = ?               ");

        oldPassword = MD5Util.getMD5(oldPassword, username);
        sql.setPara(username, oldPassword);
        List<Map<String, Object>> requestList = sql.query();

        if (requestList.size() == 0) {
            map.put("msg", "原密码输入错误！");
            map.put("flag", "1");
            return map;
        }

        // 修改新密码
        newPassword = MD5Util.getMD5(newPassword, username);

        sql.clear();
        sql.addSql("  update user set password = ?  ");
        sql.addSql("   where username = ?           ");
        sql.setPara(newPassword, username);
        int count = sql.update();

        if (count == 0) {
            map.put("msg", "密码修改失败，请重试！");
            map.put("flag", "1");
            return map;
        }

        map.put("msg", "密码修改成功！");
        map.put("flag", "0");
        return map;

    }
}
