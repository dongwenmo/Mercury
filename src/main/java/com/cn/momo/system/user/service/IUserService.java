package com.cn.momo.system.user.service;

import com.cn.momo.exception.BusinessException;
import com.cn.momo.system.user.dto.LoginUserDTO;
import com.cn.momo.system.user.dto.UserCacheDTO;
import com.cn.momo.system.user.pojo.User;

import java.util.HashMap;

/**
 * 系统用户
 * dongwenmo 2020-04-15
 */
public interface IUserService {
	
	/**
	 * 获取accessToken
	 * dongwenmo 2021-05-16
	 */
	String getAccessToken() throws BusinessException;
	
	/**
	 * 获取用户信息
	 * dongwenmo 2021-05-16
	 */
	UserCacheDTO getUser() throws BusinessException;

	// 通过id查找用户
	User selectByPrimaryKey(int userid);

	// 条件查询单个对象
	User selectOne(User user);

	// 验证用户登录
	User doLogin(User user) throws BusinessException;

	// 验证用户登录，成功返回token
	String loginForToken(LoginUserDTO user) throws BusinessException;

	// 判断用户名是否存在
	boolean checkUsernameExist(User user);

	// 用户注册
	User register(User user) throws BusinessException;

	// 注册用户
	int insert(User user);

	// 通过用户名、邮箱地址发送验证码（忘记密码）
	HashMap<String,Object> sendCodeToMail(String username, String mailAccount);

	// 通过用户名、邮箱验证码修改密码（忘记密码）
	HashMap<String,Object> modifyUserPasswordByMail(String username, String code, String newPassword);

	// 通过用户名、原密码修改密码（修改密码）
	HashMap<String,Object> modifyUserPasswordByOldPassword(String username, String oldPassword, String newPassword);
}
