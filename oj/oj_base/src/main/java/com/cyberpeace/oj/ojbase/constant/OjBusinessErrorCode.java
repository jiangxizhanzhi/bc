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

	/** 习题集下存在习题*/
	String SET_UNDER_EXIST_PRACTICE = "102011";

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

	/**  对应的习题文件未找到*/
	String PRACTICE_OPERATION_FILE_NOT_FOUNT = "102034";

	/** 第x行 训练计划不能为空*/
	String TRAIN_PLAN_NAME_IS_NULL = "102035";

	/** 第x行 训练计划已存在*/
	String LINE_TRAIN_PLAN_EXIST = "102036";

	/** 第x行 训练计划对应课程不存在*/
	String PLAN_COURSE_IS_NULL = "102037";

	/** 第x行 检索不到对应课程*/
	String PLAN_COURSE_NOT_EXIST = "102038";

	/** 第x行 训练计划对应课时不能为空*/
	String PLAN_LESSON_IS_NULL = "102039";

	/** 第x行 检索不到对应课时*/
	String PLAN_LESSON_NOT_EXIST = "102040";

	/** 第x行 计划时间不能为空*/
	String PLAN_TIME_IS_NULL = "102041";

	/** 第x行 结束时间大于开始时间*/
	String PLAN_END_GT_START_TIME = "102042";

	/** 第x行 计划组织不能为空*/
	String PLAN_ORG_IS_NULL = "102043";

	/** 第x行 组织计划不存在*/
	String PLAN_ORG_NOT_EXIST = "102044";

	/** 第x行 课时未公开*/
	String PLAN_LESSON_NOT_PUBLIC = "102045";

	// 删除实例时没有传入resource_id字段
	String RESOURCE_ID_MISSING = "102046";

	/** 对应的课堂未找到*/
	String CLASSROOM_NOT_FOUNT = "102047";

	/** 训练计划不能为空*/
	String TRAIN_PLAN_ID_NOT_NULL = "102048";

	/** 检索不到对应的训练计划*/
	String TRAIN_PLAN_NOT_FOUND = "102049";

	/** 用户无课堂分组信息*/
	String USER_CLASS_GROUP_NOT_FOUND = "102050";

	/** 用户未进行远程协助请求或者协助已结束*/
	String USER_NO_NEED_HELP = "102051";

	/** 学员未接入机器*/
	String HELP_URL_NOT_FOUND = "102052";

	/** 训练计划%s已开始，暂不支持导入*/
	String CLASSROOM_START_NOT_COVER = "102053";

	/** 资源正在导入*/
	String RESOURCE_IN_IMPORT = "102054";

	/** 训练计划已存在*/
	String TRAIN_PLAN_EXIST = "102055";

	/** xxx组织下无用户*/
	String ORG_UNDER_NO_USERS = "102056";

	/** 开始时间不能为空*/
	String START_TIME_NOT_NULL = "102057";

	/** 结束时间不能为空*/
	String END_TIME_NOT_NULL = "102058";

	/** 第xxx行 文件内容缺失*/
	String FILE_CONTENT_MISSING = "102059";

	/** 评论不存在*/
	String COMMENT_NOT_EXIST = "102060";

	/** 未检索到课时信息*/
	String NOT_SELECT_LESSON_INFORMATION = "102061";

	/** 留言内容不能为空*/
	String MESSAGE_CONTENT_IS_NULL = "102062";

	/** 第xxx行 文件内容缺失*/
	String TIME_FORMAT_ERROR = "102063";

	/** 导出resource_id错误 资源不存在*/
    String RESOURCE_ID_NOT_EXIST = "102067";

	/** 该课程包含课时没有所属章节*/
	String LESSON_WITHOUT_CHAPTER = "102072";

	/** 实操习题导出失败*/
	String OPERATION_EXPORT_FAILED = "102068";

	/** 附件数量和答案长度不一致*/
	String PRACTICE_ATTACHMENT_ANSWER_INCONSISTENT = "102065";

	/** 课程无LOGO*/
	String NO_DEFAULT_COURSE_LOGO = "102079";

	/** 实操习题集导出失败*/
	String OPERATION_SET_EXPORT_FAILED = "102069";

	/** 实操习题查询失败*/
	String OPERATION_EXPORT_QUERY_FAILED = "102070";

	/** 部分附件缺失*/
	String ATTACHMENT_MISSION = "102071";

	/** xxx习题集无LOGO*/
	String PRACTICE_SET_NO_LOGO = "102074";

}
