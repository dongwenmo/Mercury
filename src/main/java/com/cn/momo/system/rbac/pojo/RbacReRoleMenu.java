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
* auto 2021-01-25 18:56:10
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rbac_re_role_menu")
public class RbacReRoleMenu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reId;// 关联id
	private Integer roleId;// 角色id
	private Integer menuId;// 菜单id
	private String type;// 权限类型：1-有；2-禁
}
