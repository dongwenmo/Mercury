package com.cn.momo.system.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录DTO
 * dongwenmo 2021-04-08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDTO {
    private String username;// 用户名
    private String password;// 密码（一次md5加密后）
    private String appId;// 应用id
    private String ip;// IP地址
}
