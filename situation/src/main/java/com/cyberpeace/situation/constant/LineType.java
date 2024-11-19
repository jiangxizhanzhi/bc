package com.cyberpeace.situation.constant;

import com.cyberpeace.common.model.CommonContext;

import lombok.Getter;

/**
 * 线型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Enum
 * @since 1.0.0
 */
public enum LineType {

	/**
	 * 实直线
	 *
	 * @see LineType
	 */
	STRAIGHT("straight", "实直线"),
	/**
	 * 虚直线
	 *
	 * @see LineType
	 */
	DASHED_STRAIGHT("dashed_straight", "虚直线"),
	/**
	 * 实飞线
	 *
	 * @see LineType
	 */
	FLY("fly", "实飞线"),
	/**
	 * 虚飞线
	 *
	 * @see LineType
	 */
	DASHED_FLY("dashed_fly", "虚飞线");

	/**
	 * 代号
	 *
	 * @see String
	 */
	@Getter
	private String code;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@Getter
	private String desc;

	LineType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static String getRandomValue(LineType[] values) {

		return values[(int) (Math.random() * values.length)].code;

	}

	public String getMsg() {
		return CommonContext.isChinese() ? desc : code;
	}

}
