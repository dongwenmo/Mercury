package com.cn.momo.system.rbac.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author dongwenmo
 * @create 2020-08-02 10:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "menu")
public class Menu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer menuId;// 菜单id
	private Integer menuParentId;// 父级菜单id
	private String menuName;// 菜单名称
	private String menuType;// 菜单类型
	private String url;// 菜单地址
	private String menuDesc;// 描述
	private Integer priority;// 序号
}
