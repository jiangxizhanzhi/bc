package com.cyberpeace.bs.constant;

/**
 * 用户行为模拟自定义错误码
 * @author SN
 */
public interface BsErrorCode {

	/**
	 * 仪表已开始
	 */
	String ALREADY_STARTED = "105001";

	/**
	 * 无效的访问配置
	 */
	String INVALID_ACCESS_CONF = "105002";

	/**
	 * 无效的行为配置
	 */
	String INVALID_BEHAVIOR_CONF = "105003";

	/**
	 * 行为开始时间未配置
	 */
	String START_TIME_NOT_CONFIGURED = "105004";

	/**
	 * id列表为空
	 */
	String EMPTY_ID_COLL = "105005";
}
