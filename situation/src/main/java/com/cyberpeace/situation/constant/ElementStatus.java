package com.cyberpeace.situation.constant;

import lombok.Getter;

/**
 * 节点状态
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Enum
 * @since 1.0.0
 */
public enum ElementStatus {

	/**
	 * 未启动
	 *
	 * @see ElementStatus
	 */

	UNSTARTED("unstarted", "未启动"),
	/**
	 * 创建中
	 *
	 * @see ElementStatus
	 */
	CREATING("creating", "创建中"),
	/**
	 * 启动中
	 *
	 * @see ElementStatus
	 */
	STARTING("starting", "启动中"),
	/**
	 * 异常
	 *
	 * @see ElementStatus
	 */
	ABNORMAL("abnormal", "异常"),
	/**
	 * 运行中
	 *
	 * @see ElementStatus
	 */
	RUNNING("running", "运行中"),
	/**
	 * 离线
	 *
	 * @see ElementStatus
	 */
	OFFLINE("offline","离线");

	/**
	 * 代号
	 *
	 * @see String
	 */
	@Getter
	private String code;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@Getter
	private String desc;

	ElementStatus(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static String getRandomValue(ElementStatus[] values) {

		return values[(int) (Math.random() * values.length)].code;

	}

}
