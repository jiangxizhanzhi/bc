package com.cyberpeace.scene.constant.enums;

import lombok.Getter;

public enum SceneEventStatusEnum {

	PREPARE(1, "准备执行", ""), ONGOING(2, "执行过程中", ""), FINISH(4, "执行结束", ""), ERROR(5, "执行错误", "");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	@Getter
	private String msg;

	SceneEventStatusEnum(Integer code, String desc, String msg) {
		this.code = code;
		this.desc = desc;
		this.msg = msg;
	}
}
