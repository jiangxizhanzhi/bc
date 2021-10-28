package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum NoteResourceType implements GenericEnum {

	COURSE(1, "课程"),

	LESSON(2, "课时"),

	PRACTICE(3, "练习"),

	LESSON_PAPER(4, "课后练习");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	NoteResourceType(Integer code, String desc) {

	}

}
