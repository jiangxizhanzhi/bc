package com.cyberpeace.cs.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

/**
 * 操作对象
 */
public enum Operator implements GenericEnum {

	BC(1, "bc");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	Operator(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
