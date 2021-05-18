package com.cn.momo.system.user.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 用户表
 * user_id
 * username
 * password
 * nick_name
 * create_time
 * cancel_time
 * identity_id
 * token
 * last_ip
 * code
 *
 * @author dongwenmo
 * @create 2020-04-15 16:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer userId;//用户id
    private String username;//用户名
    private String password;//密码
    private String nickName;//昵称
    private Date createTime;//创建时间
    private Integer identityId;//实名认证
    private String token;//token
    private String lastIp;//上一次登录的ip
}
