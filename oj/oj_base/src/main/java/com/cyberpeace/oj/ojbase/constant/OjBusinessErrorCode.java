package com.cyberpeace.oj.ojbase.constant;

/**
 * 靶场自定义错误码。
 * 1、从102001开始递增编号；
 * 2、截止编号地址102999；
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
public interface OjBusinessErrorCode {

	/**
	 * 习题集类型错误
	 *
	 * @see String
	 */
	String PRACTICE_SET_TYPE_ERROR = "102001";

	/**
	 * 习题分类类型错误
	 *
	 * @see String
	 */
	String PRACTICE_CATEGORY_TYPE_ERROR = "102002";

	/**
	 * 习题集数量不足
	 *
	 * @see String
	 */
	String PRACTICE_COUNT_NOT_ENOUGH = "102003";

	/**
	 * 资源无权限
	 *
	 * @see String
	 */
	String RESOURCE_PERMISSION = "102004";

	/**
	 * 课堂场景已启动
	 *
	 * @see String
	 */
	String CLASS_SCENE_IS_RUNNING = "102005";

	/**
	 * 课堂场景启动失败
	 *
	 * @see String
	 */
	String SCENE_START_FAIL = "102006";

	/**
	 * 字段数据过长
	 *
	 * @see String
	 */
	String CONTENT_FILED_DATA_TOO_LONG = "102007";

	/**
	 * 名称已存在
	 *
	 * @see String
	 */
	String NAME_IS_EXIST = "102008";

	/**
	 * 资源已存在
	 *
	 * @see String
	 */
	String RESOURCE_IS_EXIST = "102808";

	/**
	 * 课程编号已存在
	 *
	 * @see String
	 */
	String COURSE_NUM_IS_EXIST = "102009";

	/**
	 * 结束时间必须大于开始时间
	 *
	 * @see String
	 */
	String END_GT_START_TIME = "102010";

	/**
	 * 习题集下存在习题
	 *
	 * @see String
	 */
	String SET_UNDER_EXIST_PRACTICE = "102011";

	/**
	 * 选择组织下无用户
	 *
	 * @see String
	 */
	String ORGANIZATION_IS_NO_USERS = "102012";

	/**
	 * 资源正在被使用
	 *
	 * @see String
	 */
	String RESOURCE_USED_NOT_DELETED = "102013";

	/**
	 * 该班级在该时间段内已有课程安排
	 *
	 * @see String
	 */
	String CLASS_IN_SCHEDULES = "102014";

	/**
	 * 已存在组织和课时相同的课程安排
	 *
	 * @see String
	 */
	String CLASS_LESSON_ORG_SAME = "102015";

	/**
	 * 存在运行的场景
	 *
	 * @see String
	 */
	String SCENE_IS_RUNNING = "102016";

	/**
	 * 场景运行过多
	 *
	 * @see String
	 */
	String SCENE_RUNNING_TOO_MUCH = "102017";

	/**
	 * 字段数据过长
	 *
	 * @see String
	 */
	String FIELD_TOO_LONG = "102018";

	/**
	 * 讲义内容错误
	 *
	 * @see String
	 */
	String LESSON_HANDOUT_CONTENT_ERROR = "102019";

	/**
	 * 课时目录名称错误
	 *
	 * @see String
	 */
	String LESSON_DIR_NAME_ERROR = "102020";

	/**
	 * 课堂未签到
	 *
	 * @see String
	 */
	String CLASS_NOT_SIGN = "102021";

	/**
	 * 其他机器正在进行远程协助
	 *
	 * @see String
	 */
	String OTHER_NODE_IN_ASSISTANCE = "102022";

	/**
	 * 非动态环境，无法进行场景启动
	 *
	 * @see String
	 */
	String NON_DYNAMIC_ENVIRONMENT = "102023";

	/**
	 * 场景未启动
	 *
	 * @see String
	 */
	String SCENE_NOT_RUNNING = "102024";

	/**
	 * 场景暂停中
	 *
	 * @see String
	 */
	String SCENE_IS_PAUSE = "102025";

	/**
	 * 请重新加载题目
	 *
	 * @see String
	 */
	String RELOAD_PRACTICE = "102026";

	/**
	 * 父类别不存在
	 *
	 * @see String
	 */
	String PARENT_CATEGORY_NOT_EXIST = "102027";

	/**
	 * 答案不能为空
	 *
	 * @see String
	 */
	String ANSWER_NOT_NULL = "102028";

	/**
	 * 答案长度限制在xxx-xxx
	 *
	 * @see String
	 */
	String ANSWER_TOO_LONG = "102029";

	/**
	 * 场景启动成功xxx个，失败xxx个
	 *
	 * @see String
	 */
	String ROOM_SCENE_BATCH_FAILED = "102030";

	/**
	 * 课程信息不能为空
	 *
	 * @see String
	 */
	String COURSE_INFORMATION_NOT_NULL = "102031";

	/**
	 * 课时信息不能为空
	 *
	 * @see String
	 */
	String LESSON_INFORMATION_NOT_NULL = "102032";

	/**
	 * 组织信息不能为空
	 *
	 * @see String
	 */
	String ORG_INFORMATION_NOT_NULL = "102033";

	/**
	 * 对应的习题文件未找到
	 *
	 * @see String
	 */
	String PRACTICE_OPERATION_FILE_NOT_FOUNT = "102034";

	/**
	 * 第x行 训练计划不能为空
	 *
	 * @see String
	 */
	String TRAIN_PLAN_NAME_IS_NULL = "102035";

	/**
	 * 第x行 训练计划已存在
	 *
	 * @see String
	 */
	String LINE_TRAIN_PLAN_EXIST = "102036";

	/**
	 * 第x行 训练计划对应课程不存在
	 *
	 * @see String
	 */
	String PLAN_COURSE_IS_NULL = "102037";

	/**
	 * 第x行 检索不到对应课程
	 *
	 * @see String
	 */
	String PLAN_COURSE_NOT_EXIST = "102038";

	/**
	 * 第x行 训练计划对应课时不能为空
	 *
	 * @see String
	 */
	String PLAN_LESSON_IS_NULL = "102039";

	/**
	 * 第x行 检索不到对应课时
	 *
	 * @see String
	 */
	String PLAN_LESSON_NOT_EXIST = "102040";

	/**
	 * 第x行 计划时间不能为空
	 *
	 * @see String
	 */
	String PLAN_TIME_IS_NULL = "102041";

	/**
	 * 第x行 结束时间大于开始时间
	 *
	 * @see String
	 */
	String PLAN_END_GT_START_TIME = "102042";

	/**
	 * 第x行 计划组织不能为空
	 *
	 * @see String
	 */
	String PLAN_ORG_IS_NULL = "102043";

	/**
	 * 第x行 组织计划不存在
	 *
	 * @see String
	 */
	String PLAN_ORG_NOT_EXIST = "102044";

	/**
	 * 第x行 课时未公开
	 *
	 * @see String
	 */
	String PLAN_LESSON_NOT_PUBLIC = "102045";

	/**
	 * 资源id失踪
	 *
	 * @see String
	 */ // 删除实例时没有传入resource_id字段
	String RESOURCE_ID_MISSING = "102046";

	/**
	 * 对应的课堂未找到
	 *
	 * @see String
	 */
	String CLASSROOM_NOT_FOUNT = "102047";

	/**
	 * 训练计划不能为空
	 *
	 * @see String
	 */
	String TRAIN_PLAN_ID_NOT_NULL = "102048";

	/**
	 * 检索不到对应的训练计划
	 *
	 * @see String
	 */
	String TRAIN_PLAN_NOT_FOUND = "102049";

	/**
	 * 用户无课堂分组信息
	 *
	 * @see String
	 */
	String USER_CLASS_GROUP_NOT_FOUND = "102050";

	/**
	 * 用户未进行远程协助请求或者协助已结束
	 *
	 * @see String
	 */
	String USER_NO_NEED_HELP = "102051";

	/**
	 * 学员未接入机器
	 *
	 * @see String
	 */
	String HELP_URL_NOT_FOUND = "102052";

	/**
	 * 训练计划%s已开始，暂不支持导入
	 *
	 * @see String
	 */
	String CLASSROOM_START_NOT_COVER = "102053";

	/**
	 * 资源正在导入
	 *
	 * @see String
	 */
	String RESOURCE_IN_IMPORT = "102054";

	/**
	 * 训练计划已存在
	 *
	 * @see String
	 */
	String TRAIN_PLAN_EXIST = "102055";

	/**
	 * xxx组织下无用户
	 *
	 * @see String
	 */
	String ORG_UNDER_NO_USERS = "102056";

	/**
	 * 开始时间不能为空
	 *
	 * @see String
	 */
	String START_TIME_NOT_NULL = "102057";

	/**
	 * 结束时间不能为空
	 *
	 * @see String
	 */
	String END_TIME_NOT_NULL = "102058";

	/**
	 * 第xxx行 文件内容缺失
	 *
	 * @see String
	 */
	String FILE_CONTENT_MISSING = "102059";

	/**
	 * 评论不存在
	 *
	 * @see String
	 */
	String COMMENT_NOT_EXIST = "102060";

	/**
	 * 未检索到课时信息
	 *
	 * @see String
	 */
	String NOT_SELECT_LESSON_INFORMATION = "102061";

	/**
	 * 留言内容不能为空
	 *
	 * @see String
	 */
	String MESSAGE_CONTENT_IS_NULL = "102062";

	/**
	 * 第xxx行 文件内容缺失
	 *
	 * @see String
	 */
	String TIME_FORMAT_ERROR = "102063";

	/**
	 * xx习题附件过多
	 *
	 * @see String
	 */
	String PRACTICE_ATTACHMENT_IS_TOO_MANY = "102064";

	/**
	 * 附件数量和答案长度不一致
	 *
	 * @see String
	 */
	String PRACTICE_ATTACHMENT_ANSWER_INCONSISTENT = "102065";

	/**
	 * 方法未实现
	 *
	 * @see String
	 */
	String NOT_IMPLEMENTED_EXCEPTION = "102066";

	/**
	 * 导出resource_id错误 资源不存在
	 *
	 * @see String
	 */
	String RESOURCE_ID_NOT_EXIST = "102067";

	/**
	 * 实操习题导出失败
	 *
	 * @see String
	 */
	String OPERATION_EXPORT_FAILED = "102068";

	/**
	 * 实操习题集导出失败
	 *
	 * @see String
	 */
	String OPERATION_SET_EXPORT_FAILED = "102069";

	/**
	 * 实操习题查询失败
	 *
	 * @see String
	 */
	String OPERATION_EXPORT_QUERY_FAILED = "102070";

	/**
	 * 部分附件缺失
	 *
	 * @see String
	 */
	String ATTACHMENT_MISSION = "102071";

	/**
	 * 该课程包含课时没有所属章节
	 *
	 * @see String
	 */
	String LESSON_WITHOUT_CHAPTER = "102072";

	/**
	 * 课程导出失败
	 *
	 * @see String
	 */
	String COURSE_EXPORT_FAILED = "102073";

	/**
	 * xxx习题集无LOGO
	 *
	 * @see String
	 */
	String PRACTICE_SET_NO_LOGO = "102074";

	/**
	 * 传入类型参数错误
	 *
	 * @see String
	 */
	String TYPE_ERROR = "102075";

	/**
	 * 讲师最大字符数为64
	 *
	 * @see String
	 */
	String MAX_64_CHARACTERS = "102076";

	/**
	 * 课程分数最大字符数为1000
	 *
	 * @see String
	 */
	String MAX_SCORE_1000 = "102077";

	/**
	 * 习题方向最长为128
	 *
	 * @see String
	 */
	String MAX_PRACTICE_CATEGORY_128 = "102078";

	/**
	 * 课程无LOGO
	 *
	 * @see String
	 */
	String NO_DEFAULT_COURSE_LOGO = "102079";

	/**
	 * 选择题选项过多
	 *
	 * @see String
	 */
	String CHOICE_OPTION_TOO_MANY = "102080";

	/**
	 * 存在课时和组织相同的数据
	 *
	 * @see String
	 */
	String DUPLICATE_DATA_ORG_LESSON = "102081";

	/**
	 * 存在组织相同时间冲突的数据
	 *
	 * @see String
	 */
	String TIME_INTERSECTION_UNDER_ORG = "102082";

	/**
	 * 习题类型存在错误
	 *
	 * @see String
	 */
	String PRACTICE_TYPE_ERROR = "102083";

	/**
	 * 选择题答案格式错误
	 *
	 * @see String
	 */
	String CHOICE_ANSWER_ERROR = "102084";

	/**
	 * 填空题答案至少一个
	 *
	 * @see String
	 */
	String FILL_BLANK_TOO_LITTLE = "102085";

	/**
	 * 选择题选项至少两个
	 *
	 * @see String
	 */
	String CHOICE_OPTION_TOO_LITTLE = "102086";

	/**
	 * 判断题答案格式错误
	 *
	 * @see String
	 */
	String JUDGEMENT_ANSWER_ERROR = "102087";

	/**
	 * 判断题答案格式错误
	 *
	 * @see String
	 */
	String SHORT_ANSWER_ANSWER_EMPTY_ERROR = "102088";

	/**
	 * 关键字不能设置为空
	 *
	 * @see String
	 */
	String 	KEYWORD_EMPTY_ERROR = "102089";

	/**
	 * 习题类型存在错误
	 *
	 * @see String
	 */
	String 	PRACTICE_DIFFICULTY_ERROR = "102090";

	/**
	 * 理论基础数据无效上传失败
	 *
	 * @see String
	 */
	String IMPORT_THEORY_INVALID = "102091";

	/**
	 * 习题方向为空
	 *
	 * @see String
	 */
	String PRACTICE_DIRECTION_EMPTY_ERROR = "102092";

	/**
	 * 课时类型不正确，当前类型为xxx
	 *
	 * @see String
	 */
	String ACCESS_LESSON_TYPE = "102093";

	/**
	 * 所有学员未提交报告
	 *
	 * @see String
	 */
	String NO_REPORT_CAN_BE_EXPORTED = "102094";

	/**
	 * 该班级在该时间段内已有课程安排
	 *
	 * @see String
	 */
	String IMPORT_CLASS_IN_SCHEDULES = "102095";

	/**
	 * 第x行 训练计划对应课程无权限
	 *
	 * @see String
	 */
	String PLAN_COURSE_NO_PERMISSION = "102096";

	/**
	 * 习题集名称重复
	 *
	 * @see String
	 */
	String PRACTICE_SET_IS_EXIST = "102097";

	/**
	 * 重复导入相同名称课时
	 *
	 * @see String
	 */
	String REPEAT_IMPORT_SAME_LESSON_NAME = "102098";

	/**
	 * xxx为内置资源，无法导出
	 *
	 * @see String
	 */
	String UNABLE_TO_EXPORT_BUILTIN_RESOURCE = "102099";

	/**
	 * xxx习题集中的xxx习题为内置资源，无法导出
	 *
	 * @see String
	 */
	String PRACTICE_SET_CONTAIN_BUILTIN_PRACTICE = "102100";

	/**
	 * xxx课程中的xxx课时为内置资源，无法导出
	 *
	 * @see String
	 */
	String COURSE_CONTAIN_BUILTIN_LESSON = "102101";

	/**
	 * xxx课程中的xxx课时中的xxx习题为内置资源，无法导出
	 *
	 * @see String
	 */
	String LESSON_CONTAIN_BUILTIN_PRACTICE = "102102";

	/**
	 * 已提交的实验报告无法再存草稿
	 *
	 * @see String
	 */
	String CANT_SAVE_SUBMIT_REPORT = "102123";

	/**
	 * 无法对已经通过的实验报告进行提交
	 *
	 * @see String
	 */
	String CANT_SUBMIT_EVALUATE_REPORT = "102124";

	/**
	 * 组卷时根据所选条件找不到足够的题目数
	 *
	 * @see String
	 */
	String NOT_ENOUGH_PRACTICE = "102125";

	/**
	 * 原题目已删除，无法收藏
	 *
	 * @see String
	 */
	String CANT_COLLECT_DELETE_PRACTICE = "102126";

	/**
	 * 题目状态未选
	 *
	 * @see String
	 */
	String QUESTION_STATUS_NOT_SELECTED = "102127";

	/**
	 * 题目难度未选
	 *
	 * @see String
	 */
	String QUESTION_DIFFICULTY_NOT_SELECTED = "102128";

	/**
	 * 题目类型未选
	 *
	 * @see String
	 */
	String QUESTION_TYPE_NOT_SELECTED = "102129";

	/**
	 * 题目方向未选
	 *
	 * @see String
	 */
	String QUESTION_DIRECTION_NOT_SELECTED = "102130";

	/**
	 * 出题范围错误
	 *
	 * @see String
	 */
	String PRACTICE_RANGE_ERROR = "102131";
	/**
	 * writeup 无效
	 *
	 * @see String
	 */
	String WRITEUP_INVALID = "102132";
	String VIDEO_DURATION_NOT_NULL = "102133";
	/**
	 * 不支持随机FLAG
	 *
	 * @see String
	 */
	String RANDOM_FLAG_NOT_SUPPORTED = "102134";
}
