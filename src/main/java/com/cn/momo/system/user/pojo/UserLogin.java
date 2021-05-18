package com.cn.momo.system.user.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author dongwenmo
 * @create 2020-04-27 12:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_login")
public class UserLogin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer loginId;
	private Integer userId;
	private Date loginTime;
	private String loginIp;
	private String flag;
}
