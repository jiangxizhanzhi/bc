package com.cyberpeace.situation.constant;

import lombok.Getter;

public enum LineType {

	/**
	 * 线型
	 */

	STRAIGHT("straight", "实直线"),
	DASHED_STRAIGHT("dashed_straight", "虚直线"),
	FLY("fly", "实飞线"),
	DASHED_FLY("dashed_fly", "虚飞线");

	@Getter
	private String code;

	@Getter
	private String desc;

	LineType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static String getRandomValue(LineType[] values) {

		return values[(int) (Math.random() * values.length)].code;

	}

}
