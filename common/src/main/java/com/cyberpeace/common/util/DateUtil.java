package com.cyberpeace.common.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {

	public static final String SHOT_PATTERN = "yyyy-MM-dd";

	public static final Map<String, String> CN_DATE_MAP = new HashMap<String, String>(){{
		put("0", "0");
		put("1", "一");
		put("2", "二");
		put("3", "三");
		put("4", "四");
		put("5", "五");
		put("6", "六");
		put("7", "七");
		put("8", "八");
		put("9", "九");
	}};

	public static String DateToString(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	public static Date StringToDate(String strDate, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		ParsePosition pos = new ParsePosition(0);
		return formatter.parse(strDate, pos);
	}

	public static String StringToCnDateString(String strDate) {
		StringBuilder stringBuilder = new StringBuilder();
		for (char c : strDate.toCharArray()) {
			String k = String.valueOf(c);
			stringBuilder.append(CN_DATE_MAP.getOrDefault(k, k));
		}
		return stringBuilder.toString();
	}
}
