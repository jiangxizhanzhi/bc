package com.cyberpeace.integratation.constant;

/**
 * 会话类型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Enum
 * @since 1.0.0
 */
public enum SessionType {

	/**
	 * 监控路径和与远程协助路径
	 *
	 * @see SessionType
	 */
	ALL("all", "监控路径和与远程协助路径"),
	/**
	 * 仅获取远程协助路径
	 *
	 * @see SessionType
	 */
	ASSISTANCE("assistance", "仅获取远程协助路径");

	/**
	 * 类型
	 *
	 * @see String
	 */
	public String type;

	/**
	 * 描述
	 *
	 * @see String
	 */
	public String desc;

	SessionType(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

}