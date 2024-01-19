package com.cyberpeace.scene.constant.enums;

import lombok.Getter;

public enum SceneEventEnum {

	INIT(1, "初始化", "场景正在创建"), CREATE(2, "创建", "场景正在创建"), RUNNING(3, "开启", "场景正在启动"), DELETED(9,
			"删除", "场景已删除");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	@Getter
	private String msg;

	SceneEventEnum(Integer code, String desc, String name) {
		this.code = code;
		this.desc = desc;
		this.msg = name;
	}
}
