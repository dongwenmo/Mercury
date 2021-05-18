package com.cn.momo.system.rbac.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author auto
 * @create 2021-01-19 16:31:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rbac_menu")
public class RbacMenu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer menuId;// 菜单id
	private Integer menuParentId;// 父菜单id
	private String type;// 菜单类型：1-目录；2-页面
	private String name;// 唯一标识
	private String icon;// 图标
	private String path;// 路由地址
	private String title;// 菜单名称
	private Integer priority;// 优先级
}
