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

	/** 场景运行过多*/
	String SCENE_RUNNING_TOO_MUCH = "102017";

	/** 字段数据过长*/
	String FIELD_TOO_LONG = "102018";

	/** 讲义内容错误*/
	String LESSON_HANDOUT_CONTENT_ERROR = "102019";

	/** 课时目录名称错误*/
	String LESSON_DIR_NAME_ERROR = "102020";

	/** 课堂未签到*/
	String CLASS_NOT_SIGN = "102021";

	/** 其他机器正在进行远程协助*/
	String OTHER_NODE_IN_ASSISTANCE = "102022";

	/** 非动态环境，无法进行场景启动*/
	String NON_DYNAMIC_ENVIRONMENT = "102023";

	/** 场景未启动*/
	String SCENE_NOT_RUNNING = "102024";

	/** 场景暂停中*/
	String SCENE_IS_PAUSE = "102025";

	/** 请重新加载题目*/
	String RELOAD_PRACTICE = "102026";

	String PARENT_CATEGORY_NOT_EXIST = "102027";

	/** 答案不能为空*/
	String ANSWER_NOT_NULL = "102028";

	/** 答案长度限制在xxx-xxx*/
	String ANSWER_TOO_LONG = "102029";

	/** 场景启动成功xxx个，失败xxx个*/
	String ROOM_SCENE_BATCH_FAILED = "102030";

	/** 课程信息不能为空*/
	String COURSE_INFORMATION_NOT_NULL = "102031";

	/** 课时信息不能为空*/
	String LESSON_INFORMATION_NOT_NULL = "102032";

	/** 组织信息不能为空*/
	String ORG_INFORMATION_NOT_NULL = "102033";

}
