package com.cyberpeace.situation.constant;

import lombok.Getter;

/**
 * 大屏模式
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum DashboardMode {

	/**
	 * GIS/拓扑
	 */
	SPG(1, "GIS/TOPO"),

	/**
	 * 拓扑/GIS
	 */
	SPT(2, "TOPO/GIS"),

	/**
	 * GIS
	 */
	GIS(3, "GIS"),

	/**
	 * 拓扑
	 */
	TOPO(4, "TOPO");

	/**
	 * 代码
	 */
	@Getter
	private Integer code;

	/**
	 * 名称
	 */
	@Getter
	private String name;

	DashboardMode(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

}
