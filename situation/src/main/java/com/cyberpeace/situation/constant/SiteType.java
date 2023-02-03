package com.cyberpeace.situation.constant;

import lombok.Getter;

public enum SiteType {

	/**
	 * 站点类型
	 */

	CENTER("RL", "LG中心"), RANGE("RF", "分靶场"), STATION("RC", "场站");

	@Getter
	private String code;

	@Getter
	private String name;

	SiteType(String code, String name) {
		this.code = code;
		this.name = name;
	}

}
