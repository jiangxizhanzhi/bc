package com.cyberpeace.situation.constant;

import com.cyberpeace.situation.model.ViewAngle;
import lombok.Getter;

public enum MapView {

	/**
	 * 默认视角
	 */

	ZH("zh", "中国", new ViewAngle("120.358", "22.295", 2256291, 281.3f, 267.2f, 0f)),
	BJ("bj", "北京", new ViewAngle("120.358", "22.295", 2256291, 281.3f, 267.2f, 0f));

	@Getter
	private String code;

	@Getter
	private String name;

	@Getter
	private ViewAngle viewAngle;

	MapView(String code, String name, ViewAngle viewAngle) {
		this.code = code;
		this.name = name;
		this.viewAngle = viewAngle;
	}

}
