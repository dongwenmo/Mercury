package com.cn.momo.annotation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 解析注解
 * dongwenmo 2020-07-23 8:43
 */
public class AnnotationParse {
	/**
	 * 解析权限注解
	 * dongwenmo 2020-07-23
	 */
	public static String prmissionParse(Method method) {
		//获取该方法
		if (method.isAnnotationPresent(Permission.class)) {
			Permission annotation = method.getAnnotation(Permission.class);
			return annotation.authorities();
		}
		return null;
	}

	/**
	 * 解析调用日志注解
	 * dongwenmo 2020-08-20
	 */
	public static Map<String, String> callLogParse(Method method) {
		//获取该方法
		if (method.isAnnotationPresent(CallLog.class)) {
			CallLog callLog = method.getAnnotation(CallLog.class);
			String name = callLog.name();
			String desc = callLog.desc();
			Map<String, String> map = new HashMap<>();
			map.put("name", name);
			map.put("desc", desc);
			return map;
		}
		return null;
	}

}
