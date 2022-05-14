package com.cyberpeace.cs.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

/**
 * 目录划分
 */
public enum Operator implements GenericEnum {

	CR(1, "cr");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	Operator(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
