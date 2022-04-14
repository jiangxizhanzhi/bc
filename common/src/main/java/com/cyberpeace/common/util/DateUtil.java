package com.cyberpeace.common.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {

	public static final String SHOT_PATTERN = "yyyy-MM-dd";

	public static final Map<String, String> CN_DATE_YEAR_MAP = new HashMap<String, String>(){{
		put("0", "〇");
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

	public static final Map<String, String> CN_DATE_MONTH_MAP = new HashMap<String, String>(){{
		put("01", "一");
		put("02", "二");
		put("03", "三");
		put("04", "四");
		put("05", "五");
		put("06", "六");
		put("07", "七");
		put("08", "八");
		put("09", "九");
		put("10", "十");
		put("11", "十一");
		put("12", "十二");
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

	public static String DateToCnDateString(Date date) {
		String strDate = new SimpleDateFormat("yyyy年MM月").format(date);

		String year = strDate.substring(0, 4);
		String month = strDate.substring(5, 7);
		StringBuilder stringBuilder = new StringBuilder();
		for (char c : year.toCharArray()) {
			stringBuilder.append(CN_DATE_YEAR_MAP.get(String.valueOf(c)));
		}

		strDate = strDate.replace(year, stringBuilder.toString());
		strDate = strDate.replace(month, CN_DATE_MONTH_MAP.get(month));

		return strDate;
	}
}
