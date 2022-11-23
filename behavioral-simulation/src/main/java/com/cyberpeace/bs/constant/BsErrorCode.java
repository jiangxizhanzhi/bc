package com.cyberpeace.bs.constant;

/**
 * 用户行为模拟自定义错误码
 * @author SN
 */
public interface BsErrorCode {

	String ALREADY_STARTED = "105001";

	String INVALID_ACCESS_CONF = "105002";

	String INVALID_BEHAVIOR_CONF = "105003";

	/**
	 * 行为开始时间未配置
	 */
	String START_TIME_NOT_CONFIGURED = "105004";
}
