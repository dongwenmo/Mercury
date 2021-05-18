package com.cn.momo.config;

/**
 * 错误代码配置类
 * dongwenmo 2021-05-16
 */
public class ErrorConfig {
    public static final int ERROR = -1;// 获取用户信息失败
    
    // 权限错误
    public static final int ERR_10001 = 10001;// accessToken不存在
    public static final int ERR_10002 = 10002;// 获取用户信息失败
    public static final int ERR_10003 = 10003;// 获取HttpServletRequest失败
    public static final int ERR_10004 = 10004;// 登录已过期：根据accessToken未从缓存中获取到对应的用户信息
    public static final int ERR_10005 = 10005;// 登录：密码错误
    public static final int ERR_10006 = 10006;// 登录：该用户不存在
    public static final int ERR_10007 = 10007;// 注册：该用户已存在
    
    // 参数校验
    public static final int ERR_11001 = 11001;// 参数不存在或值为空
    
    // 数据库
    public static final int ERR_12001 = 12001;// 插入数据库失败
}
