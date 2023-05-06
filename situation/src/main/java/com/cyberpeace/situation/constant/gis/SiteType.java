package com.cyberpeace.situation.constant.gis;

import lombok.Getter;

/**
 * 站点类型
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum SiteType {

	/**
	 * LG中心
	 */
	CENTER("RL", "LG中心"),

	/**
	 * 分靶场
	 */
	RANGE("RF", "分靶场"),

	/**
	 * 场站
	 */
	STATION("RC", "场站");

	/**
	 * 代码
	 */
	@Getter
	private String code;

	/**
	 * 名称
	 */
	@Getter
	private String name;

	SiteType(String code, String name) {
		this.code = code;
		this.name = name;
	}

}
