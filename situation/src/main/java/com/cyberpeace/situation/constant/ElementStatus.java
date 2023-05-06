package com.cyberpeace.situation.constant;

import lombok.Getter;

/**
 * 节点状态
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum ElementStatus {

	/**
	 * 未启动
	 */
	UNSTARTED("unstarted", "未启动"),

	/**
	 * 创建中
	 */
	CREATING("creating", "创建中"),

	/**
	 * 启动中
	 */
	STARTING("starting", "启动中"),

	/**
	 * 异常
	 */
	ABNORMAL("abnormal", "异常"),

	/**
	 * 运行中
	 */
	RUNNING("running", "运行中"),

	/**
	 * 离线
	 */
	OFFLINE("offline", "离线");

	/**
	 * 代码
	 */
	@Getter
	private String code;

	/**
	 * 描述
	 */
	@Getter
	private String desc;

	ElementStatus(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 获取随机状态代码
	 *
	 * @param values 值
	 * @return {@code String}
	 */
	public static String getRandomValue(ElementStatus[] values) {

		return values[(int) (Math.random() * values.length)].code;

	}

}
