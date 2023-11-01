package com.cyberpeace.situation.constant;

import com.cyberpeace.common.util.MathUtil;
import lombok.Getter;

public enum ElementStatus {

	/**
	 * 节点状态
	 */

	UNSTARTED("unstarted", "未启动"),
	CREATING("creating", "创建中"),
	STARTING("starting", "启动中"),
	ABNORMAL("abnormal", "异常"),
	RUNNING("running", "运行中"),
	OFFLINE("offline","离线");

	@Getter
	private String code;

	@Getter
	private String desc;

	ElementStatus(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static String getRandomValue(ElementStatus[] values) {

		return values[MathUtil.getRandomInt(values.length)].code;

	}

}
