package com.cyberpeace.integratation.constant;

import com.cyberpeace.constant.BaseI18n;
import lombok.Getter;

public enum LogContent implements BaseI18n {
	/**
	 * 操作日志内容描述前缀
	 */

	CR_FORMAT("%s %s \"%s\"", "%s%s \"%s\""),
	COURSE_FORMAT("Name of course: %s, Name of lesson: %s.", "课程名：%s，课时名：%s。"),
	PRACTICE_FORMAT("Exercise: \"%s\", the user answered %s.", "实操题：\"%s\"，用户回答%s。"),
	THEORY_FORMAT("Theory: \"%s\", the user answered %s.", "理论题：\"%s\"，用户回答%s。"),
	EXERCISE("Exercise", "实操题"),
	THEORY("Theory", "理论题"),
	CORRECT("correctly", "正确"),
	INCORRECT("incorrectly", "错误");

	@Getter
	private final String english;

	@Getter
	private final String chinese;

	LogContent(String english, String chinese) {
		this.english = english;
		this.chinese = chinese;
	}
}

