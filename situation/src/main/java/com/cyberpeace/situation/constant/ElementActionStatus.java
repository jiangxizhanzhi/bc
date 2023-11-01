package com.cyberpeace.situation.constant;

import com.cyberpeace.common.util.MathUtil;
import lombok.Getter;

public enum ElementActionStatus {

	/**
	 * 节点动作状态
	 */

	NORMAL("normal", "正常"),
	COMPROMISED("compromised", "被攻陷"),
	RUINED("ruined", "被销毁");

	@Getter
	private String code;

	@Getter
	private String desc;

	ElementActionStatus(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static String getRandomValue(ElementActionStatus[] values) {

		return values[MathUtil.getRandomInt(values.length)].code;

	}

}
