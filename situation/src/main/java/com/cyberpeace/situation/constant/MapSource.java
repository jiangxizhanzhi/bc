package com.cyberpeace.situation.constant;

import lombok.Getter;

public enum MapSource {

	/**
	 * 地图源
	 */

	GG(1, "谷歌"), BD(2, "百度"), GD(3, "高德");

	@Getter
	private Integer code;

	@Getter
	private String name;

	MapSource(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

}
