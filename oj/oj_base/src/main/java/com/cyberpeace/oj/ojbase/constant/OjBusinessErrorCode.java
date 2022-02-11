package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.constant.BaseI18nErrorMsg;
import lombok.Getter;

/**
 * 靶场自定义错误码。
 *  1、从102001开始递增编号；
 *  2、截止编号地址102999；
 */
public enum OjBusinessErrorCode implements BaseI18nErrorMsg {

	/** 习题集类型错误*/
	PRACTICE_SET_TYPE_ERROR("102001", "习题集类型错误", "Practice set type error."),

	/** 习题分类类型错误*/
	PRACTICE_CATEGORY_TYPE_ERROR("102002", "习题分类类型错误", "Practice set category type error."),

	/** 习题集数量不足*/
	PRACTICE_COUNT_NOT_ENOUGH("102003", "习题数量不足", "The number of practice sets is insufficient."),

	/** 资源无权限*/
	RESOURCE_PERMISSION("102004", "权限不足", "Resource not permission."),

	/** 课堂场景已启动*/
	CLASS_SCENE_IS_RUNNING("102005", "课堂场景运行中", "Class Scene is running."),

	/** 课堂场景启动失败*/
	SCENE_START_FAIL("102006", "场景启动失败", "Scene startup failure."),

	/** 字段数据过长*/
	FILED_DATA_TOO_LONG("102007", "某字段数据过长", "The field data is too long."),

	/** 名称已存在*/
	NAME_IS_EXIST("102008", "名称已存在", "Name already exists."),

	/** 课程编号已存在*/
	COURSE_NUM_IS_EXIST("102009", "课程编号已存在", "Course number already exists."),

	/** 结束时间必须大于开始时间*/
	END_GT_START_TIME("102010", "结束时间必须大于开始时间", "The end time must be greater than the start time."),

	/** 课程表时间范围为上午八点-晚上21点*/
	CLASSROOM_TIME_RANGE_ERROR("102011", "课程表时间范围为上午8点-晚上21点", "Time is out of range, only 8 to 21."),

	/** 选择组织下无用户*/
	ORGANIZATION_IS_NO_USERS("102012", "选择组织下无用户", "Select No users in the organization."),

	/** 资源正在被使用*/
	RESOURCE_USED_NOT_DELETED("102013", "资源正在被使用", "Resource is being used."),

	/** 该班级在该时间段内已有课程安排*/
	CLASS_IN_SCHEDULES("102014", "该班级在该时间段内已有课程安排", "The class has courses during this time."),

	/** 已存在组织和课时相同的课程安排*/
	CLASS_LESSON_ORG_SAME("102015", "已存在组织和课时相同的课程安排", "A course schedule with the same organization and lesson already exists."),

	/** 存在运行的场景*/
	SCENE_IS_RUNNING("102016", "场景运行中", "Scene is running."),

	SCENE_RUNNING_TOO_MUCH("102017", "场景数量启动过多", "Too many scene are started");


	@Getter
	private final String chinese;

	@Getter
	private final String english;

	@Getter
	private final String errorCode;

	OjBusinessErrorCode(String errorCode, String chinese, String english) {
		this.errorCode = errorCode;
		this.chinese = chinese;
		this.english = english;
	}

}
