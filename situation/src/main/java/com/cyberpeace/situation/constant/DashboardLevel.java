package com.cyberpeace.situation.constant;

import lombok.Getter;

public enum DashboardLevel {

	/**
	 * 大屏层级
	 */

	LGZX(1, "LG中心综合态势"),
	FBC(2, "分靶场综合态势"),
	RW(3, "任务态势");

	@Getter
	private Integer code;

	@Getter
	private String name;

	DashboardLevel(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

}
