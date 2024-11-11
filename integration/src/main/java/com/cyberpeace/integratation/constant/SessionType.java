package com.cyberpeace.integratation.constant;

/**
 * 会话类型
 *
 * @author SN
 * @date 2024/11/11
 */
public enum SessionType {

	/**
	 * 都
	 */
	ALL("all", "监控路径和与远程协助路径"),
	/**
	 * 援助
	 */
	ASSISTANCE("assistance", "仅获取远程协助路径");

	/**
	 * 类型
	 */
	public String type;

	/**
	 * 描述
	 */
	public String desc;

	SessionType(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

}