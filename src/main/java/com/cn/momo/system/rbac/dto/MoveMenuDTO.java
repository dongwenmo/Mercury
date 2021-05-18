package com.cn.momo.system.rbac.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 移动菜单节点入参
 * dongwenmo 2021-01-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoveMenuDTO {
    private Integer menuId;// 菜单id
    private Integer menuAimId;// 目标菜单id
    private String type;// 移动类型
}
