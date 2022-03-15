package com.cyberpeace.situation.constant;

import lombok.Getter;

public enum AreaShape {

	/**
	 * 区域形状
	 */

	RECTANGLE("rectangle", "矩形");

	@Getter
	private String code;

	@Getter
	private String desc;

	AreaShape(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
