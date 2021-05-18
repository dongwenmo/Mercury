package com.cn.momo.util;

import com.cn.momo.exception.BusinessException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author dongwenmo
 * @create 2020-04-28 19:04
 */
public class DateUtil {
	// 传入日期字符串、日期格式化，返回日期类型数据
	public static Date parseDate(String date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * @desc: 传入日期，返回字符串
	 * @author: dongwenmo
	 * @create: 2020-09-05 16:58:11
	 * @param date: 日期
	 * @param format: 格式
	 * @return: java.lang.String
	 */
	public static String format(Date date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * @desc: 传入日期，返回字符串
	 * @author: dongwenmo
	 * @create: 2020-09-05 16:58:22
	 * @param format: 格式（日期默认为当前日期）
	 * @return: java.lang.String
	 */
	public static String format(String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	/**
	 * @desc: 传入日期，返回字符串
	 * @author: dongwenmo
	 * @create: 2020-09-05 16:58:45
	 * @param date: 日期（格式默认为：yyyy-MM-dd HH:mm:ss）
	 * @return: java.lang.String
	 */
	public static String format(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * @desc: 传入日期，返回字符串（日期默认当前日期，格式默认yyyy-MM-dd HH:mm:ss）
	 * @author: dongwenmo
	 * @create: 2020-09-05 16:59:51
	 * @return: java.lang.String
	 */
	public static String format(){
		return format(new Date());
	}

	// 返回两个时间的差值
	public static long timeDiff(Date starttime, Date endtime){
		return endtime.getTime() - starttime.getTime();
	}

	// 增加分钟
	public static Date addMinute(Date date, int minute) {
		return new Date(date.getTime() + (long) (minute) * 60 * 1000);
	}

	// 增加小时
	public static Date addHour(Date date, int hour) {
		return addMinute(date, hour * 60);
	}

	// 增加天数
	public static Date addDay(Date date, int day) {
		return addMinute(date, day * 60 * 24);
	}

	// 获取指定日期所在周，周一的日期
	public static String getWeekStartDay(Date date){
		date = parseDate(format(date, "yyyy-MM-dd"), "yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);  // 美国是以周日为每周的第一天 现把周一设成第一天
		calendar.setTime(date);

		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return format(addDay(date, 1 - dayOfWeek), "yyyy-MM-dd");
	}

	// 获取指定日期的详细信息
	public static Map<String, Object> getDayInfo(Date date){
		Map<String, Object> map = new HashMap<>();

		Calendar calendar = Calendar.getInstance();
		// 美国是以周日为每周的第一天 现把周一设成第一天
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		String[] weekDays = {"日", "一", "二", "三", "四", "五", "六"};

		map.put("year", calendar.get(Calendar.YEAR));
		map.put("month", calendar.get(Calendar.MONTH) + 1);
		map.put("date", calendar.get(Calendar.DATE));
		map.put("day", format(date, "yyyy-MM-dd"));

		map.put("dayOfYear", calendar.get(Calendar.DAY_OF_YEAR));
		map.put("dayOfMonth", calendar.get(Calendar.DAY_OF_MONTH));
		map.put("dayOfWeek", calendar.get(Calendar.DAY_OF_WEEK));

		map.put("dayOfWeekText1", weekDays[calendar.get(Calendar.DAY_OF_WEEK)-1]);
		map.put("dayOfWeekText2", "周"+weekDays[calendar.get(Calendar.DAY_OF_WEEK)-1]);
		map.put("dayOfWeekText3", "星期"+weekDays[calendar.get(Calendar.DAY_OF_WEEK)-1]);
		map.put("dayOfWeek", calendar.get(Calendar.DAY_OF_WEEK));

		map.put("weekOfYear", calendar.get(Calendar.WEEK_OF_YEAR));
		map.put("weekOfMonth", calendar.get(Calendar.WEEK_OF_MONTH));

		if(format(date,"yyyyMMdd").equals(format(new Date(), "yyyyMMdd"))){
			map.put("today", "1");
		}else{
			map.put("today", "0");
		}

		return map;
	}

	// 获取指定日期的详细信息
	public static Map<String, Object> getDayInfo(String dateStr, String format){
		return getDayInfo(parseDate(dateStr, format));
	}

	// 获取指定日期所在周，每一天的详细信息
	public static List<Map<String, Object>> getWeekInfo(Date date){
		List<Map<String, Object>> list = new ArrayList<>();
		String weekStartDay = getWeekStartDay(date);
		Date day = parseDate(weekStartDay, "yyyy-MM-dd");
		for(int i=0;i<7;i++){
			list.add(getDayInfo(day));
			day = addDay(day, 1);
		}
		return list;
	}

	/**
	 * 获取指定日期所在周，每一天的详细信息
	 * @param dateStr 日期字符串
	 * @param format 日期格式
	 */
	public static List<Map<String, Object>> getWeekInfo(String dateStr, String format){
		return getWeekInfo(parseDate(dateStr, format));
	}

	/**
	 * 获取指定日期所在周，每一天的详细信息
	 * @param date 日期
	 * @param week 0为本周，-1为上周，-为下周，以此类推
	 */
	public static List<Map<String, Object>> getWeekInfo(Date date, int week){
		return getWeekInfo(addDay(date, 7*week));
	}

	/**
	 * 根据日期字符串获取获取日期格式
	 * @param date 日期字符串
	 * @return
	 */
	public static String getFormat(String date) throws BusinessException {
		int len = date.length();
		if(len == 19){
			if(date.contains("-") && date.contains(":")){
				return "yyyy-MM-dd HH:mm:ss";
			}else if(date.contains(".") && date.contains(":")){
				return "yyyy.MM.dd HH:mm:ss";
			}
		}else if(len == 10){
			if(date.contains("-")){
				return "yyyy-MM-dd";
			}else if(date.contains(".")){
				return "yyyy.MM.dd";
			}
		}else if(len == 14){
			return "yyyyMMddHHmmss";
		}else if(len == 8){
			return "yyyyMMdd";
		}
		throw new BusinessException("日期字符串格式错误");
	}

}
