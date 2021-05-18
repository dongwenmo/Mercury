package com.cn.momo.system.rbac.service.impl;

import com.cn.momo.common.BaseServiceImpl;
import com.cn.momo.system.rbac.mapper.RbacReUserRoleMapper;
import com.cn.momo.system.rbac.pojo.RbacReUserRole;
import com.cn.momo.system.rbac.pojo.RbacRole;
import com.cn.momo.system.rbac.service.IRbacReUserRoleService;
import com.cn.momo.system.rbac.service.IRbacRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
*
* auto 2021-01-26 13:40:37
*/
@Service
public class RbacReUserRoleServiceImpl extends BaseServiceImpl<RbacReUserRole> implements IRbacReUserRoleService {
    @Autowired
    private RbacReUserRoleMapper rbacReUserRoleMapper;

    @Override
    public int switchUserRole(RbacReUserRole rbacReUserRole) {
        List<RbacReUserRole> list = rbacReUserRoleMapper.select(rbacReUserRole);
        if(list.size() == 0){
            rbacReUserRoleMapper.insertSelective(rbacReUserRole);
        }else{
            rbacReUserRoleMapper.delete(rbacReUserRole);
        }
        return 1;
    }
}
