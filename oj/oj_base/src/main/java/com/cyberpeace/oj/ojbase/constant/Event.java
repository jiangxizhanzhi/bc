package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum Event implements GenericEnum {

	SCENE_UPDATE(1, "场景启动中 状态持续更新");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	Event(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
