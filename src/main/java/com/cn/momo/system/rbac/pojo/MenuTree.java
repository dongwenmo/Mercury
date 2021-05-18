package com.cn.momo.system.rbac.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author dongwenmo
 * @create 2020-08-01 22:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuTree {
	private Integer menuId;// 菜单id
	private Integer menuParentId;// 父菜单id
	private String content;// 菜单名称
	private String url;// 地址
	private List<MenuTree> children;
}
