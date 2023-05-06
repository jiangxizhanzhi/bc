package com.cyberpeace.situation.constant;

import lombok.Getter;

/**
 * 大屏类型
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum DashboardType {

	/**
	 * 定制
	 */
	DZ(1, "定制"),

	/**
	 * 自编
	 */
	ZB(2, "自编"),

	/**
	 * 三方靶标
	 */
	SF(3, "三方靶标");

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

	DashboardType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

}
