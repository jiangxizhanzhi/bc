package com.cyberpeace.situation.constant;

import lombok.Getter;

public enum DashboardMode {

	/**
	 * 大屏模式
	 */

	SPG(1, "GIS/TOPO"),
	SPT(2, "TOPO/GIS"),
	GIS(3, "GIS"),
	TOPO(4, "TOPO");

	@Getter
	private Integer code;

	@Getter
	private String name;

	DashboardMode(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

}
