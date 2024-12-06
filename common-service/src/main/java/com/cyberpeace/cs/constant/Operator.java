package com.cyberpeace.cs.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

/**
 * 操作对象
 *
 * @author SN
 * @date 2024/11/11
 */
@Getter
public enum Operator implements GenericEnum {

	/**
	 * 公元前
	 */
	BC(1, "bc");

	/**
	 * 代号
	 */
	private final Integer code;

	/**
	 * 描述
	 */
	private final String desc;

	Operator(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
