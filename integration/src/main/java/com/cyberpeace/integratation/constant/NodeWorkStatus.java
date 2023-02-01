package com.cyberpeace.integratation.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

public enum NodeWorkStatus implements GenericEnum {

	/**
	 * 节点工作状态
	 */
	RUNNING(0, "工作中"), OFFSET(1, "离线");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	NodeWorkStatus(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
