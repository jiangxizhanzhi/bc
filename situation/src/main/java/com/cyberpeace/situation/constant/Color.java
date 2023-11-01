package com.cyberpeace.situation.constant;

import com.cyberpeace.common.model.CommonContext;

import com.cyberpeace.common.util.MathUtil;
import lombok.Getter;

public enum Color {

	/**
	 * 颜色
	 */

	RED("#FF0000", "红色", "red"),
	YELLOW("#FFFF00","黄色","yellow"),
	BLUE("#0000FF", "蓝色", "blue"),
	WHITE("#FFFFFF", "白色", "white"),
	GOLD("#CD7F32","金色","gold"),
	GREEN("#00FF00", "绿色", "green"),
	GREY("#1B1B1B", "灰色", "grey");

	@Getter
	private String code;

	@Getter
	private String desc;

	@Getter
	private String enDesc;

	Color(String code, String desc, String enDesc) {
		this.code = code;
		this.desc = desc;
		this.enDesc = enDesc;
	}

	public static String getRandomValue(Color[] values) {
		return values[MathUtil.getRandomInt(values.length)].code;

	}

	public String getMsg() {
		return CommonContext.isChinese() ? desc : enDesc;
	}
}
