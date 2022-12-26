package com.cyberpeace.situation.constant;

import lombok.Getter;

public enum DashboardType {

	/**
	 * 大屏类型
	 */

	DZ(1, "定制"),
	ZB(2, "自编");

	@Getter
	private Integer code;

	@Getter
	private String name;

	DashboardType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

}
