package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

/**
 * 困难
 *
 * @author SN
 * @date 2024/11/11
 */
public enum Difficulty implements GenericEnum {

	/**
	 * 主要
	 */
	PRIMARY(0, "初级"),
	/**
	 * 中间
	 */
	MIDDLE(1, "中级"),
	/**
	 * 高
	 */
	HIGH(2, "高级");

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

	Difficulty(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static String ofCode(Integer code) {
		if(code == 0) {
			return PRIMARY.getDesc();
		}
		if(code == 1) {
			return MIDDLE.getDesc();
		}
		if(code == 2) {
			return HIGH.getDesc();
		}
		return "";
	}
}
