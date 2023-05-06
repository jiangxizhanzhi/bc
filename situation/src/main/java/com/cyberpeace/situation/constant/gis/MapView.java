package com.cyberpeace.situation.constant.gis;

import com.cyberpeace.situation.model.ViewAngle;

import lombok.Getter;

/**
 * 地图视角
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum MapView {

	/**
	 * 中国视角
	 */
	ZH("zh", "中国", new ViewAngle("120.358", "22.295", 2256291, 281.3f, 267.2f, 0f)),

	/**
	 * 北京视角
	 */
	BJ("bj", "北京", new ViewAngle("120.358", "22.295", 2256291, 281.3f, 267.2f, 0f));

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

	/**
	 * 视角
	 */
	@Getter
	private ViewAngle viewAngle;

	MapView(String code, String name, ViewAngle viewAngle) {
		this.code = code;
		this.name = name;
		this.viewAngle = viewAngle;
	}

}
