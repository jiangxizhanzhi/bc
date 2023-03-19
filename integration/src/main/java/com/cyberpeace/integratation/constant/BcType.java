package com.cyberpeace.integratation.constant;

import lombok.Getter;

public enum BcType {

	/**
	 * 站点类型
	 */
	LGZX("1", "LG中心"),
	MY("2", "民"),
	JY("3", "J"),
	ZCZ("4", "主场站"),
	FCZ("5", "分场站");

	@Getter
	private String code;

	@Getter
	private String name;

	BcType(String code, String name) {
		this.code = code;
		this.name = name;
	}
}
