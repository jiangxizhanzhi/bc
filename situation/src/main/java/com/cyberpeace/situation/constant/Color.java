package com.cyberpeace.situation.constant;

import com.cyberpeace.common.model.CommonContext;

import lombok.Getter;

public enum Color {

	/**
	 * 颜色
	 */

	// WHITE("#FFFFFF", "白色"),
	// BLACK("#000000", "黑色"),
	RED("#FF0000", "红色", "red"),
	BLUE("#0000FF", "蓝色", "blue");

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

		return values[(int) (Math.random() * values.length)].code;

	}

	public String getMsg() {
		return CommonContext.isChinese() ? desc : enDesc;
	}

}
