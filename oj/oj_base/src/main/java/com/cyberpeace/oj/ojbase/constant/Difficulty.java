package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

public enum Difficulty implements GenericEnum {

	PRIMARY(0, "初级"), MIDDLE(1, "中级"), HIGH(2, "高级");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	Difficulty(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
