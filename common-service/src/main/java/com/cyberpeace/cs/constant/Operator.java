package com.cyberpeace.cs.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

/**
 * 操作对象
 *
 * @author SN
 * @date 2024/11/11
 */
public enum Operator implements GenericEnum {

	/**
	 * 公元前
	 */
	BC(1, "bc");

	/**
	 * 代号
	 */
	@Getter
	private Integer code;

	/**
	 * 描述
	 */
	@Getter
	private String desc;

	Operator(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
