package com.cyberpeace.common.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class DateUtil {

	public static final String SHOT_PATTERN = "yyyy-MM-dd";

	/**
	 * 日期字符串
	 *
	 * @param date    日期
	 * @param pattern 模式
	 * @return {@link String}
	 */
	public static String DateToString(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	/**
	 * 字符串日期
	 *
	 * @param strDate str日期
	 * @param pattern 模式
	 * @return {@link Date}
	 */
	public static Date StringToDate(String strDate, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(strDate, pos);
	}
}
