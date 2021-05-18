package com.cn.momo.system.rbac.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
*
* auto 2021-01-26 13:40:37
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rbac_re_user_role")
public class RbacReUserRole {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reId;// 关联id
	private Integer userId;// 用户id
	private Integer roleId;// 角色id
}
