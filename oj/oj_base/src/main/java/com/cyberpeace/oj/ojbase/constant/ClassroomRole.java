package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import com.cyberpeace.constant.BaseI18n;
import lombok.Getter;

public enum ClassroomRole implements GenericEnum, BaseI18n {

	/** 课堂角色枚举*/
	TEACHER(0, "教员", "teacher"),
	STUDENT(1, "学员", "student"),
	CLASSROOM(2, "课堂", "classroom");

	@Getter
	private Integer code;

	@Getter
	private String chinese;

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
