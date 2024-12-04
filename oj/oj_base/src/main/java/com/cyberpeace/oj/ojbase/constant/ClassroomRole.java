package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import com.cyberpeace.constant.BaseI18n;
import lombok.Getter;

/**
 * 课堂岗位
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @see GenericEnum
 * @see BaseI18n
 * @since 1.0.0
 */
public enum ClassroomRole implements GenericEnum, BaseI18n {

	/**
	 * 课堂角色枚举
	 *
	 * @see ClassroomRole
	 */
	TEACHER(0, "教员", "teacher"),
	/**
	 * 学员
	 *
	 * @see ClassroomRole
	 */
	STUDENT(1, "学员", "student"),
	/**
	 * 课堂
	 *
	 * @see ClassroomRole
	 */
	CLASSROOM(2, "课堂", "classroom");

	/**
	 * 代号
	 *
	 * @see Integer
	 */
	@Getter
	private Integer code;

	/**
	 * 中文
	 *
	 * @see String
	 */
	@Getter
	private String chinese;

	/**
	 * 英语
	 *
	 * @see String
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
