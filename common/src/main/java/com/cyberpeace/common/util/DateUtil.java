package com.cyberpeace.common.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * date util
 *
 * @author SN
 * @date 2024/11/07
 */
public class DateUtil {

	/**
	 * 拍摄模式
	 */
	public static final String SHOT_PATTERN = "yyyy-MM-dd";

	/**
	 * date 转字符串
	 *
	 * @param date    日期
	 * @param pattern 模式
	 * @return {@code String }
	 */
	public static String DateToString(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		if (StringUtils.isBlank(pattern)) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	/**
	 * 字符串到日期
	 *
	 * @param strDate str 日期
	 * @param pattern 模式
	 * @return {@code Date }
	 */
	public static Date StringToDate(String strDate, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(strDate, pos);
	}

	/**
	 * 是同一年
	 *
	 * @param date1 日期1
	 * @param date2 日期2
	 * @return {@code Boolean }
	 */
	public static Boolean isSameYear(Date date1, Date date2) {
		if (date1==null||date2==null) return false;
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return cal1.get(Calendar.YEAR)==cal2.get(Calendar.YEAR);
	}
}
