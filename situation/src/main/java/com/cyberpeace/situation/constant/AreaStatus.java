package com.cyberpeace.situation.constant;

import lombok.Getter;

/**
 * 面积状态
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Enum
 * @since 1.0.0
 */
public enum AreaStatus {

	/**
	 * 区域状态
	 *
	 * @see AreaStatus
	 */

	NORMAL("normal", "正常");

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

	AreaStatus(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
