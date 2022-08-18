package com.cyberpeace.cs.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

/**
 * 目录划分
 */
public enum DumpModule implements GenericEnum {

	COURSE(0, "course"),
	CTF_PRACTICE(1, "ctf");
//	/** 课程附件*/
//	ATTACHMENT(2, "oj/course_attachment/"),
//	MARKDOWN(3, "oj/course/%s/markdown/"),
//	OUTLINE(4, "oj/outline/");


	@Getter
	private Integer code;

	@Getter
	private String desc;

	@Getter
	private String originDesc;

	DumpModule(Integer code, String desc) {
		this.code = code;
		this.originDesc = desc;
		this.desc = desc;
	}

}
