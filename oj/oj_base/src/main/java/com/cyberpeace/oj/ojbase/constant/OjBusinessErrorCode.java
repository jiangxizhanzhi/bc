package com.cyberpeace.oj.ojbase.constant;

/**
 * 靶场自定义错误码。
 *  1、从102001开始递增编号；
 *  2、截止编号地址102999；
 */
public interface OjBusinessErrorCode {

	/** 资源无权限*/
	String RESOURCE_PERMISSION = "102004";

	/** 课堂场景已启动*/
	String GROUP_SCENE_IS_RUNNING = "102005";

	/** 课堂场景启动失败*/
	String GROUP_SCENE_START_FAIL = "102006";

	/** 习题集类型错误*/
	String PRACTICE_SET_TYPE_ERROR = "102001";

	/** 习题分类类型错误*/
	String PRACTICE_CATEGORY_TYPE_ERROR = "102002";

	/** 习题集数量不足*/
	String PRACTICE_COUNT_NOT_ENOUGH = "102003";

	/** 字段数据过长*/
	String FILED_DATA_TOO_LONG = "102004";

}
