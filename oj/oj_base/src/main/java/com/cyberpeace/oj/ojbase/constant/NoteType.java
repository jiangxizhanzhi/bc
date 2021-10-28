package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum NoteType implements GenericEnum {

	NOTE(1, "笔记"),

	REPORT(2, "实验报告");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	NoteType(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
