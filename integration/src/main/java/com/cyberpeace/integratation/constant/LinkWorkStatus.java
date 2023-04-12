package com.cyberpeace.integratation.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

public enum LinkWorkStatus implements GenericEnum {

	/**
	 * 链路工作状态
	 */
	RUNNING(0, "畅通"),
	MILD_CROWDING(1, "轻度拥挤"),
	MODERATE_CROWDING(2, "中度拥挤"),
	SEVERE_CROWDING(3, "重度拥挤"),
	OFFSET(4, "未连通");


	@Getter
	private Integer code;

	@Getter
	private String desc;

	LinkWorkStatus(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
