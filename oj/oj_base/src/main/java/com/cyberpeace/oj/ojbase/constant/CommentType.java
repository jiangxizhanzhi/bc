package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum CommentType implements GenericEnum {

	COURSE(1, "课程"),

	LESSON(2, "课时"),

	EXERCISE(3, "实操题");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	CommentType(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
