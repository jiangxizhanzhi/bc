package com.cyberpeace.cs.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

/**
 * 操作对象
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Enum
 * @see GenericEnum
 * @since 1.0.0
 */
public enum Operator implements GenericEnum {

	/**
	 * 公元前
	 *
	 * @see Operator
	 */
	BC(1, "bc");

	/**
	 * 代号
	 *
	 * @see Integer
	 */
	@Getter
	private Integer code;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@Getter
	private String desc;

	Operator(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
