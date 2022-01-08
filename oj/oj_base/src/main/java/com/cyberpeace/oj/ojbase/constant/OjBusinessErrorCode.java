package com.cyberpeace.oj.ojbase.constant;

/**
 * 靶场自定义错误码。
 *  1、从102001开始递增编号；
 *  2、截止编号地址102999；
 */
public interface OjBusinessErrorCode {

	/** 习题集类型错误*/
	String PRACTICE_SET_TYPE_ERROR = "102001";

	/** 习题分类类型错误*/
	String PRACTICE_CATEGORY_TYPE_ERROR = "102002";

	/** 习题集数量不足*/
	String PRACTICE_COUNT_NOT_ENOUGH = "102003";

	/** 资源无权限*/
	String RESOURCE_PERMISSION = "102004";

	/** 课堂场景已启动*/
	String SCENE_IS_RUNNING = "102005";

	/** 课堂场景启动失败*/
	String SCENE_START_FAIL = "102006";

	/** 字段数据过长*/
	String FILED_DATA_TOO_LONG = "102007";

	/** 名称已存在*/
	String NAME_IS_EXIST = "102008";

	/** 课程编号已存在*/
	String COURSE_NUM_IS_EXIST = "102009";

}
