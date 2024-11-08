package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import com.cyberpeace.constant.BaseI18n;
import lombok.Getter;

/**
 * 课堂岗位
 *
 * @author SN
 * @date 2024/11/08
 */
public enum ClassroomRole implements GenericEnum, BaseI18n {

	/**
	 * 课堂角色枚举
	 */
	TEACHER(0, "教员", "teacher"),
	/**
	 * 学员
	 */
	STUDENT(1, "学员", "student"),
	/**
	 * 课堂
	 */
	CLASSROOM(2, "课堂", "classroom");

	/**
	 * 代号
	 */
	@Getter
	private Integer code;

	/**
	 * 中文
	 */
	@Getter
	private String chinese;

	/**
	 * 英语
	 */
	@Getter
	private String english;

	ClassroomRole(int code, String chinese, String english) {
		this.code = code;
		this.chinese = chinese;
		this.english = english;
	}

	@Override
	public String getDesc() {
		return getMsg();
	}
}
