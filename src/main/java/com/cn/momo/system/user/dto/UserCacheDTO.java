package com.cn.momo.system.user.dto;

import com.cn.momo.system.user.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户缓存DTO
 * dongwenmo 2021-04-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCacheDTO {
    private Integer userId;// 用户id
    private String username;// 用户名
    private String appId;// 应用id
    private String ip;// IP地址
    private String token;// 签名
    private Date loginTime;// 本次登录时间 

    public UserCacheDTO(User user){
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.ip = user.getLastIp();
        this.token = user.getToken();
        this.loginTime = new Date();
    }
}
