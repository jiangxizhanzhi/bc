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
	String CLASS_SCENE_IS_RUNNING = "102005";

	/** 课堂场景启动失败*/
	String SCENE_START_FAIL = "102006";

	/** 字段数据过长*/
	String FILED_DATA_TOO_LONG = "102007";

	/** 名称已存在*/
	String NAME_IS_EXIST = "102008";

	/** 课程编号已存在*/
	String COURSE_NUM_IS_EXIST = "102009";

	/** 结束时间必须大于开始时间*/
	String END_GT_START_TIME = "102010";

	/** 课程表时间范围为上午八点-晚上21点*/
	String CLASSROOM_TIME_RANGE_ERROR = "102011";

	/** 选择组织下无用户*/
	String ORGANIZATION_IS_NO_USERS = "102012";

	/** 资源正在被使用*/
	String RESOURCE_USED_NOT_DELETED = "102013";

	/** 该班级在该时间段内已有课程安排*/
	String CLASS_IN_SCHEDULES = "102014";

	/** 已存在组织和课时相同的课程安排*/
	String CLASS_LESSON_ORG_SAME = "102015";

	/** 存在运行的场景*/
	String SCENE_IS_RUNNING = "102016";

}
