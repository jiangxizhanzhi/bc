package com.cyberpeace.situation.constant;

import com.cyberpeace.common.model.CommonContext;

import lombok.Getter;

/**
 * 颜色
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Enum
 * @since 1.0.0
 */
public enum Color {

	/**
	 * 颜色
	 *
	 * @see Color
	 */

	RED("#FF0000", "红色", "red"),
	/**
	 * 黄色
	 *
	 * @see Color
	 */
	YELLOW("#FFFF00","黄色","yellow"),
	/**
	 * 蓝
	 *
	 * @see Color
	 */
	BLUE("#0000FF", "蓝色", "blue"),
	/**
	 * 白
	 *
	 * @see Color
	 */
	WHITE("#FFFFFF", "白色", "white"),
	/**
	 * 金
	 *
	 * @see Color
	 */
	GOLD("#CD7F32","金色","gold"),
	/**
	 * 绿
	 *
	 * @see Color
	 */
	GREEN("#00FF00", "绿色", "green"),
	/**
	 * 灰色
	 *
	 * @see Color
	 */
	GREY("#1B1B1B", "灰色", "grey");

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

	/**
	 * 英文描述
	 *
	 * @see String
	 */
	@Getter
	private String enDesc;

	Color(String code, String desc, String enDesc) {
		this.code = code;
		this.desc = desc;
		this.enDesc = enDesc;
	}

	public static String getRandomValue(Color[] values) {

		return values[(int) (Math.random() * values.length)].code;

	}

	public String getMsg() {
		return CommonContext.isChinese() ? desc : enDesc;
	}

}
