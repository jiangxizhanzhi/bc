package com.cyberpeace.cs.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

/**
 * 操作符
 * 操作对象
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public enum Operator implements GenericEnum {

	BC(1, "bc");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	/**
	 * 操作符
	 *
	 * @param code 代码
	 * @param desc desc
	 */
	Operator(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
