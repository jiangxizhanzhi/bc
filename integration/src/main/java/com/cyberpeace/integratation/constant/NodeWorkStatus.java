package com.cyberpeace.integratation.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

public enum NodeWorkStatus implements GenericEnum {

	/**
	 * 节点工作状态
	 */
	RUNNING(0, "运行中"),
	OFFSET(1, "已离线"),
	DAMAGED(2, "已破坏"),
	DESTROY(3, "摧毁"),
	OPERATE(4, "操纵"),
	HIGH_CRITICALITY(5, "关键度高"),
	CAPTURED(6, "被攻陷");


	@Getter
	private Integer code;

	@Getter
	private String desc;

	NodeWorkStatus(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
