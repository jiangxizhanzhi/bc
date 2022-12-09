package com.cyberpeace.bs.constant;

/**
 * 用户行为模拟辅助常量
 * @author SN
 */
public interface BsConstants {

	int SECONDS_IN_A_HOUR = 60 * 60;

	int MINUTE_PER_HOUR = 60;
	/**
	 * 一小时的分钟数
	 */
	Integer SECONDS_PER_MINUTE = 60;

	long MILLI_IN_A_HOUR = 60 * 60 * 1000L;

	/**
	 * 一天的毫秒数
	 */
	long MILLI_IN_A_DAY = 24 * 60 * 60 * 1000L;

	/**
	 * 使用虚拟设备时默认的设备id
	 */
	String DEFAULT_DEVICE_ID = "0";

	/**
	 * 空列表的json
	 */
	String EMPTY_LIST_JSON = "[]";

	String HOUR = "时";
}
