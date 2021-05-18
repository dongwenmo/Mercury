package com.cn.momo.util;

import java.util.Random;

/**
 * @author dongwenmo
 * @create 2020-05-04 21:45
 */
public class RandomUtil {

	// 获取0-n之间的随机数[0,n)
	public static int getNum(int n){
		Random r = new Random();
		return r.nextInt(n);
	}

	// 随机生成num位数字验证码
	public static String generateCode(int length){
		String code = "";
		for(int i=0;i<length;i++){
			code += getNum(10);
		}
		return code;
	}
}
