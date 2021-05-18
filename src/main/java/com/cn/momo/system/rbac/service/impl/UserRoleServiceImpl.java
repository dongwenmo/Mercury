package com.cn.momo.system.rbac.service.impl;

import com.cn.momo.common.BaseServiceImpl;
import com.cn.momo.system.rbac.pojo.UserRole;
import com.cn.momo.system.rbac.service.IUserRoleService;
import com.cn.momo.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dongwenmo
 * @create 2020-07-30 12:46
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements IUserRoleService {
	@Override
	public Map<String, Object> addAndModifyUserRole(UserRole userRole) {
		Map<String, Object> map = new HashMap<>();
		map.put("flag", "0");// 0成功 1失败

		// 判断角色名称
		if(StringUtil.isNull(userRole.getRoleName())){
			map.put("flag", "1");
			map.put("msg", "角色名称不能为空！");
			return map;
		}

		// 判断角色描述
		if(StringUtil.isNull(userRole.getRoleDesc())){
			map.put("flag", "1");
			map.put("msg", "角色描述不能为空！");
			return map;
		}

		// 计算角色名称拼音
		userRole.setRoleNamePinyin(StringUtil.getPinyinToLowerCase(userRole.getRoleName()));

		// 新增或修改角色信息
		if(userRole.getRoleId() == null){
			if(this.insertSelective(userRole) != 1){
				map.put("flag", "1");
				map.put("msg", "新增失败，请重试！");
				return map;
			}else{
				map.put("flag", "0");
				map.put("msg", "新增成功！");
				return map;
			}
		}else{
			if(this.updateByPrimaryKeySelective(userRole) != 1){
				map.put("flag", "1");
				map.put("msg", "修改失败，请重试！");
				return map;
			}else{
				map.put("flag", "0");
				map.put("msg", "修改成功！");
				return map;
			}
		}
	}
}
