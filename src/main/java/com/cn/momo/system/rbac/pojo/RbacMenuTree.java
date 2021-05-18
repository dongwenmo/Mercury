package com.cn.momo.system.rbac.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限菜单树
 * dongwenmo 2021-01-20
 */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class RbacMenuTree extends RbacMenu {
    private List<RbacMenuTree> children;

    public RbacMenuTree(RbacMenu rbacMenu){
        this.setMenuId(rbacMenu.getMenuId());
        this.setMenuParentId(rbacMenu.getMenuParentId());
        this.setType(rbacMenu.getType());
        this.setName(rbacMenu.getName());
        this.setIcon(rbacMenu.getIcon());
        this.setPath(rbacMenu.getPath());
        this.setTitle(rbacMenu.getTitle());
        this.setPriority(rbacMenu.getPriority());
        this.children = new ArrayList<>();
    }
}
