package com.cyberpeace.situation.constant;

import lombok.Getter;

/**
 * 节点动作状态
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum ElementActionStatus {

	/**
	 * 正常
	 */
	NORMAL("normal", "正常"),

	/**
	 * 被攻陷
	 */
	COMPROMISED("compromised", "被攻陷"),

	/**
	 * 被销毁
	 */
	RUINED("ruined", "被销毁");

	/**
	 * 代码
	 */
	@Getter
	private String code;

	/**
	 * 描述
	 */
	@Getter
	private String desc;

	ElementActionStatus(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 获取随机状态代码
	 *
	 * @param values 值
	 * @return {@code String}
	 */
	public static String getRandomValue(ElementActionStatus[] values) {

		return values[(int) (Math.random() * values.length)].code;

	}

}
