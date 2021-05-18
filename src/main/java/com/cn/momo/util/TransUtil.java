package com.cn.momo.util;

import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dongwenmo
 * @create 2020-05-04 22:17
 */
public class TransUtil {
	// int数组转字符串
	public static String ints2string(int[] array){
		String str = "";
		for(int i:array){
			str += "," + i;
		}
		if(str.length()>0){
			return str.substring(1);
		}
		return "";
	}

	// List<Integer>数组转字符串
	public static String ints2string(List<Integer> list){
		String str = "";
		for(int i:list){
			str += "," + i;
		}
		if(str.length()>0){
			return str.substring(1);
		}
		return "";
	}

	// 字符串转int数组
	public static int[] string2ints(String str){
		String[] strs = str.split(",");
		int[] array = new int[strs.length];
		for(int i=0;i<strs.length;i++){
			array[i] = Integer.parseInt(strs[i]);
		}
		return array;
	}

	// 字符串转List<Integer>数组
	public static List<Integer> string2List(String str){
		List<Integer> list = new ArrayList<>();
		if(StringUtil.isNull(str)){
			return list;
		}
		String[] strs = str.split(",");
		for(int i=0;i<strs.length;i++){
			list.add(Integer.parseInt(strs[i]));
		}
		return list;
	}

	/**
	 * byte数组转base64字符串
	 * dongwenmo 2021-03-15
	 */
	public static String byteArray2Base64(byte[] array){
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		// 返回 Base64 编码过的字节数组字符串
		String base64 = encoder.encode(array);
		base64 = base64.replace("\r\n", "");
		return base64;
	}

	/**
	 * InputStream转byte[]
	 * dongwenmo 2021-03-15
	 */
	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
		return output.toByteArray();
	}
}
