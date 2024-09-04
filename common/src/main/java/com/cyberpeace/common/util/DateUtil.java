package com.cyberpeace.common.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static final String SHOT_PATTERN = "yyyy-MM-dd";

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

	public static Date StringToDate(String strDate, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(strDate, pos);
	}

	public static Boolean isSameYear(Date date1, Date date2) {
		if (date1==null||date2==null) return false;
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return cal1.get(Calendar.YEAR)==cal2.get(Calendar.YEAR);
	}

	public static String generateTimeNamedFileName() {
		// 获取当前时间
		LocalDateTime now = LocalDateTime.now();

		// 定义时间格式
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

		// 格式化时间
		return now.format(formatter);
	}

	private DateUtil() {}
}
