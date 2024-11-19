package com.cyberpeace.situation.constant;

import lombok.Getter;

/**
 * 节点动作状态
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Enum
 * @since 1.0.0
 */
public enum ElementActionStatus {

	/**
	 * 正常
	 *
	 * @see ElementActionStatus
	 */

	NORMAL("normal", "正常"),
	/**
	 * 被攻陷
	 *
	 * @see ElementActionStatus
	 */
	COMPROMISED("compromised", "被攻陷"),
	/**
	 * 被销毁
	 *
	 * @see ElementActionStatus
	 */
	RUINED("ruined", "被销毁");

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

	ElementActionStatus(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static String getRandomValue(ElementActionStatus[] values) {

		return values[(int) (Math.random() * values.length)].code;

	}

}
