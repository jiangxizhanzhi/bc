package com.cyberpeace.oj.course.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

public enum CourseType implements GenericEnum {

	BASE(0, "基础"), ATTACK(2, "攻击"), DEFENSE(3, "防御");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	private CourseType(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
