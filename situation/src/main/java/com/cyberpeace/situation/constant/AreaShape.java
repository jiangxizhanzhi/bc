package com.cyberpeace.situation.constant;

import lombok.Getter;

/**
 * 区域形状
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Enum
 * @since 1.0.0
 */
public enum AreaShape {

	/**
	 * 区域形状
	 *
	 * @see AreaShape
	 */

	RECTANGLE("rectangle", "矩形");

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

	AreaShape(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
