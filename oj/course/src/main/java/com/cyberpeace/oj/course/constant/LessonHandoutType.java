package com.cyberpeace.oj.course.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum LessonHandoutType implements GenericEnum {

	NEITHER(0, "neither"), MD(1, "md"), PPT(2, "html"), PDF(3, "pdf");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	LessonHandoutType(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
