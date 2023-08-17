package com.cyberpeace.bs.constant;

/**
 * 用户行为模拟辅助常量
 *
 * @author SN
 */
public interface BsConstants {

    /**
     * 一小时的秒数
     */
    int SECONDS_IN_A_HOUR = 60 * 60;

    /**
     * 一小时的分钟数
     */
    int MINUTE_PER_HOUR = 60;

    /**
     * 半小时的分钟数
     */
    int MINUTE_HALF_HOUR = 30;

    /**
     * 一小时的分钟数
     */
    Integer SECONDS_PER_MINUTE = 60;

    /**
     * 一小时包含的毫秒数
     */
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

    /**
     * 小时的汉字
     */
    String HOUR = "时";

	String SCRIPT_SUFFIX = ".xlsx";

	/**
	 * 网络类型
	 *
	 * @author SN
	 * @date 2023/07/17
	 */
	interface NetworkType {

		/**
		 * 互联网
		 */
		Integer INTERNET = 1;

		/**
		 * 内网
		 */
		Integer INTRANET = 2;
	}

	/**
	 * 远程设备状态
	 *
	 * @author SN
	 * @date 2023/07/25
	 */
	interface LcDeviceStatus {

		/**
		 * 可用
		 */
		Integer AVAILABLE = 1;

		/**
		 * 被占用
		 */
		Integer OCCUPIED = 2;

		/**
		 * 离线
		 */
		Integer OFFLINE = 3;
	}
}
