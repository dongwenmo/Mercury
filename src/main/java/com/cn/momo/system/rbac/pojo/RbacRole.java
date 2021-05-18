package com.cn.momo.system.rbac.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
/**
 * @author auto
 * @create 2021-01-19 14:04:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rbac_role")
public class RbacRole {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roleId;// 角色id
	private String roleName;// 角色名称
	private String roleDesc;// 角色描述
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间
	private String state;// 状态
}
