package com.cn.momo.util;

import org.springframework.util.DigestUtils;

/**
 * MD5加密
 * @author dongwenmo
 * @create 2020-04-24 22:00
 */
public class MD5Util {
	private static final String slat = "";

	// 默认的盐
	public static String getMD5(String str) {
		String base = str + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	// 自定义盐
	public static String getMD5(String str, String slat) {
		String base = str + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
}
