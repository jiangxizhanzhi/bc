package com.cyberpeace.cr.crbase.constant;

/**
 * 靶场自定义错误码。
 * 1、从101001开始递增编号；
 * 2、截止编号地址101999；
 * <p>
 * 编号定义完成后，需要在wiki填写编号对应的错误原因。
 * wiki地址：http://10.10.10.11/pages/viewpage.action?pageId=27367377
 */
public final class CrBusinessErrorCode {

	// 找不到有效目录
	public static final String NOT_PATH = "101001";

	// 没有找到md文件
	public static final String NOT_MD_PATH = "101002";

	// 文件解压失败
	public static final String ZIP_PATH_NOT_EXTRA = "101003";

	// 实例不存在
	public static final String ROOM_NOT_EXIST = "101004";

	// 无权限操作
	public static final String PERMISSION_DENIED = "101005";

	// 训练未开始
	public static final String TRAIN_NOT_BEGIN = "101006";

	// 训练已结束
	public static final String TRAIN_IS_OVER = "101007";

	// 训练角色不存在
	public static final String ROLE_NOT_EXIST_ERROR = "101008";

	// 任务（Task）不存在
	public static final String TASK_NOT_EXIST_ERROR = "101009";

	// 评分范围出错
	public static final String SCORE_RANGE_ERROR = "101010";

	// 更新实例时没有传入resource_id字段
	public static final String RESOURCE_ID_MISSING = "101011";

	// 任务未下发，不能提交答案
	public static final String MISSION_UNRELEASED = "101012";

	// 任务处于完成状态，不可再次提交答案
	public static final String CANNOT_SUBMIT_FINISHED_TASK = "101013";

	// 指定任务不存在
	public static final String MISSION_NOT_EXIST = "101014";

	// 找不到指定的用户提交
	public static final String MISSION_SUBMIT_NOT_FOUND = "101015";

	// 任务处于驳回状态不可评分
	public static final String TASK_REJECTED = "101016";

	// 任务已下发
	public static final String MISSION_ISSUED = "101017";

	// 模板类型不支持
	public static final String TEMPLATE_TYPE_NOT_SUPPORTED = "101018";

	// 模板名称已经存在
	public static final String TEMPLATE_NAME_DUPLICATE = "101019";

	// 角色名称重复
	public static final String ROLE_NAME_DUPLICATE = "101020";

	// 场景重复启动
	public static final String SCENE_START_REPEAT = "101021";

	// 场景不存在
	public static final String LOCAL_SCENE_NOT_EXIST = "101022";

	// 启动场景时保存本地数据错误
	public static final String SAVE_LOCAL_DATA_FAILURE = "101023";

	// 文件格式错误
	public static final String FILE_FORMAT_ERROR = "101024";

	// 工具库分类在使用时不能删除
	public static final String TOOL_CATEGORY_IN_USE = "101025";

	// 指定模板不存在
	public static final String TEMPLATE_NOT_EXIST = "101026";

	// 场景未开启
	public static final String SCENE_NOT_STARTED = "101027";

	// 资源id列表为空
	public static final String EMPTY_RESOURCE_ID_LIST = "101028";

	// 启动流量失败
	public static final String START_FLOW_FAILURE = "101029";

	// 训练已开始
	public static final String TRAIN_HAS_BEGUN = "101030";

	// 训练速率范围超限
	public static final String RATE_VALUE_RANGE_ERROR = "101031";

	// 重启节点失败
	public static final String FAILED_TO_RESTART_NODE = "101032";

	// 重建节点失败
	public static final String FAILED_TO_RECREATE_NODE = "101033";

	// 场景已暂停，不能执行暂停操作
	public static final String ALREADY_PAUSED = "101034";

	// 非暂停状态不能执行恢复操作
	public static final String NOT_PAUSED = "101035";

	// 停止流量失败
	public static final String STOP_FLOW_FAILURE = "101036";

	// 非法的阶段名长度
	public static final String INVALID_STAGE_NAME = "101037";

	// 非法的任务名称长度
	public static final String INVALID_MISSION_NAME = "101038";

	// 任务方案阶段名称重复
	public static final String DUPLICATED_STAGE_NAME = "101039";

	// 阶段内任务名称重复
	public static final String DUPLICATED_MISSION_NAME = "101040";

	// 分数超出范围
	public static final String SCORE_OUT_OF_RANGE = "101041";

	// 答案不可为空
	public static final String ANSWER_NOT_NULLABLE = "101042";

	// 选项不可为空
	public static final String OPTIONS_NOT_NULLABLE = "101043";

	// 选项重复
	public static final String OPTIONS_DUPLICATE = "101044";

	// 录屏开关设置失败
	public static final String SWITCH_RECORDING_FAILURE = "101045";

	// 存在空任务模板
	public static final String EMPTY_MISSION_TEMPLATE = "101046";

	// 无效任务模板
	public static final String MISSION_TEMPLATE_INVALID = "101047";

	// 训练名称长度超出范围，应为4~40
	public static final String TRAINING_NAME_LEN_ERROR = "101048";

	// 同名训练已存在
	public static final String TRAINING_NAME_DUPLICATE = "101049";

	// 当前非训练时间
	public static final String TRAINING_NOT_RUNNING = "101050";

	// 主机已被占用
	public static final String MACHINE_OCCUPIED = "101051";

	// 角色成员不可为空
	public static final String ROLE_USERS_NOT_BE_EMPTY = "101052";

	// 任务id重复或者部分任务缺失
	public static final String MISSION_LESS = "101053";

	// 动态新增任务不可与角色既有任务重名
	public static final String DYNAMIC_MISSION_TITLE_DUPLICATE = "101054";

	// 资源包错误
	public static final String INVALID_RESOURCE_ZIP = "101055";

	// 工具名称与系统内已有名称重复
	public static final String DUPLICATED_TOOL_NAME = "101056";

	// 工具分类名称与系统内已有名称重复
	public static final String DUPLICATED_TOOL_CATEGORY_NAME = "101057";

	// 任务方案名称已存在
	public static final String DUPLICATED_MISSION_SCHEME_TEMPLATE_TITLE = "101058";

	// 暂停场景失败
	public static final String FAILED_TO_PAUSE_SCENE = "101059";

	// 恢复场景失败
	public static final String FAILED_TO_UNPAUSE_SCENE = "101060";

	// 单兵训练模板正在使用，删除失败
	public static final String SINGLE_TRAIN_TEMPLATE_IN_USE = "101061";

	// 空房间时提交任务
	public static final String ROOM_NOT_EXIST_SUBMIT_MISSION = "101062";

	// 阶段内任务不可为空
	public static final String EMPTY_MISSION_STAGE = "101063";

	// 标签个数在1~4之间
	public static final String INVALID_TAGS_NUM = "101064";

	// 标签字符长度不正确
	public static final String ILLEGAL_TAG_LEN = "101065";

	// 标签内容不能重复
	public static final String TAG_DUPLICATE = "101066";

	// 名称长度非法
	public static final String ILLEGAL_NAME_LEN = "101067";

	// 非法的技能点数值
	public static final String INVALID_SKILL_POINT_VALUE = "101068";

	// 非法的技能点个数
	public static final String INVALID_SKILL_POINTS_SIZE = "101069";

	/** 打分必须为正整数 */
	public static final String INVALID_SCORE = "101070";

	/** 只有超级管理员可以修改雷达图 */
	public static final String SUPERUSER_CAN_EDIT_RADAR = "101071";

	/** 非法的标题长度 */
	public static final String ILLEGAL_TITLE_LEN = "101072";

	/** 非法的描述长度 */
	public static final String ILLEGAL_DESC_LEN = "101073";

	/** 导出模板失败 */
	public static final String FAILED_TO_DUMP_TEMPLATE = "101074";

	/** 导出任务方案失败 */
	public static final String FAILED_TO_DUMP_COMBAT_TECH = "101075";

	/** 非法的难度 */
	public static final String INVALID_DIFFICULTY = "101076";

	/** 非法的训练类型 */
	public static final String INVALID_TRAINING_CATEGORY = "101077";

	/** 无效的角色配置信息 */
	public static final String INVALID_ROLE_INFO = "101078";

	/** 非法的任务描述长度 */
	public static final String ILLEGAL_MISSION_DESC_LEN = "101079";

	/** 训练已暂停，不能删除场景 */
	public static final String TRAINING_PAUSED = "101080";

	/** 指标项已经使用 */
	public static final String INDICATOR_ALREADY_USED = "101081";

	/** LOGO文件不存在 */
	public static final String LOGO_NOT_EXIST = "101082";

	/** 测评靶场类型不存在 */
	public static final String EVAL_CATEGORY_NOT_EXIST = "101083";

	/** 测评靶场存在运行任务 */
	public static final String RUNNING_EVAL_TASK_EXIST = "101084";

	/** 测评靶场任务不在运行 */
	public static final String EVAL_TASK_NOT_RUNNING = "101085";

	/** 测评靶场任务不存在 */
	public static final String EVAL_TASK_NOT_EXIST = "101086";

	/** 测评靶场步骤不存在 */
	public static final String EVAL_STEP_NOT_EXIST = "101087";

	/** 测评靶场步骤配置错误 */
	public static final String EVAL_STEP_ILLEGAL = "101088";

	/** 测评靶场步骤未启动 */
	public static final String EVAL_STEP_NOT_STARTED = "101089";

	/** 测评靶场步骤已启动 */
	public static final String EVAL_STEP_IS_STARTED = "101090";

	/** 测评靶场步骤已结束 */
	public static final String EVAL_STEP_IS_FINISHED = "101091";

	/** 测评靶场步骤前序步骤未完成 */
	public static final String PRECEDING_STEP_NOT_FINISHED = "101092";

	/** 算法参数重名 */
	public static final String PARAM_NAME_DUPLICATE = "101093";

	/** 未实现的方法 */
	public static final String NOT_IMPLEMENTED_EXCEPTION = "101094";

	/**
	 * 变量项格式化算法执行失败
	 */
	public static final String VARIABLE_FORMAT_EXECUTE_FAILED = "101095";

	/**
	 * 变量项匹配算法执行失败
	 */
	public static final String VARIABLE_MATCH_EXECUTE_FAILED = "101096";

	/**
	 * 变量项评分算法执行失败
	 */
	public static final String VARIABLE_MARK_EXECUTE_FAILED = "101097";

	/**
	 * 指标项执行失败
	 */
	public static final String INDEX_EXECUTE_FAILED = "101098";

	/**
	 * 三级指标项数据无效
	 */
	public static final String THIRD_INDEX_IS_FAILED = "101099";

	/**
	 * 指标体系变量项数据无效
	 */
	public static final String SYSTEM_INDEX_IS_FAILED = "101100";

	/**
	 * 模型检测算法返回数据不合规
	 */
	public static final String MATCH_ALGORITHM_RESULT_IS_INVALID = "101101";

	/**
	 * 采集变量项未获取到相关数据
	 */
	public static final String VARIABLE_DID_NOT_GET_PROBE_DATA = "101102";

	/** 未指定节点 */
	public static final String NOT_SPECIFY_NODE = "101103";

	/** 指定节点下无流量数据 */
	public static final String NO_FLOW_EXISTS_ON_NODE = "101104";

	/** 流量不存在 */
	public static final String FLOW_NOT_EXIST = "101105";

	/** 流量修改错误 */
	public static final String FAILED_TO_UPDATE_FLOW = "101106";

	/**
	 * 靶场测评评估访问中台失败
	 */
	public static final String GET_PROBE_DATA_FAILED = "101107";

	/**
	 * 当前节点存在未完成的流量任务
	 */
	public static final String FLOW_TASK_EXISTS = "101108";

	/**
	 * 无法判断流量任务状态
	 */
	public static final String FAILED_TO_JUDGE_FLOW_TASK_STATUS = "101109";

	/**
	 * 格式化算法返回数据不合规
	 */
	public static final String FORMAT_ALGORITHM_RESULT_IS_INVALID = "101110";

	/**
	 * 评分算法返回数据不合规
	 */
	public static final String MARK_ALGORITHM_RESULT_IS_INVALID = "101111";

	/**
	 * 聚合算法返回数据不合规
	 */
	public static final String AGG_ALGORITHM_RESULT_IS_INVALID = "101112";

	/** 二层框架已满 */
	public static final String SECOND_FLOOR_FRAME_IS_FULL = "101113";

	/**
	 * 标题重复
	 */
	public static final String TITLE_DUPLICATE = "101114";

	/** 非背景流量不允许修改源IP */
	public static final String NON_BACKGROUND_FLOW_NOT_ALLOWED_TO_MODIFY_SOURCE_IP = "101115";

	/** IP格式错误 */
	public static final String WRONG_IP_FORMAT = "101116";

	/** 最大支持7个分类 */
	public static final String MAX_7_CATEGORIES = "101117";

	/** 训练资管导出类型不正确 */
	public static final String EXPORT_TRAIN_ERROR_TYPE = "101118";

	/** 训练资管导出id都不存在 */
	public static final String EXPORT_TRAIN_ID_NOT_EXIST = "101119";

	/** 训练资管导出时当前训练下存在重名任务名称 */
	public static final String EXPORT_TRAIN_DUPLICATED_TASK_TITLE = "101120";

	/** 动态修改任务不可与角色既有任务重名 */
	public static final String DUPLICATE_DYNAMIC_MISSION_TITLE = "101121";

	/** 请指定一个学员 */
	public static final String PLZ_SPECIFY_ONE_STUDENT = "101122";

	/** 非法的选项长度 */
	public static final String ILLEGAL_OPTION_LEN = "101123";

	/** 工具分类英文名称与系统内已有名称重复  */
	public static final String DUPLICATED_TOOL_CATEGORY_ENNAME = "101124";

	/** 题解长度超长 */
	public static final String ILLEGAL_WRITEUP_LEN = "101125";

	// 创建启动节点失败
	public static final String FAILED_TO_CREATE_NODE = "101126";

	// 层次分析法 关联矩阵数据包含空数据 或非数字
	public static final String MATRIX_DATA_EMPTY_ERROR = "101127";

	// 内置算法禁止编辑
	public static final String BUILTIN_ALGORITHM_FORBID_DELETE_ERROR = "101128";

	// 评估已启动
	public static final String EVALUATION_HAS_BEEN_INITIATED_ERROR = "101129";

	// 评分类型错误
	public static final String SCORE_TYPE_ERROR = "101130";
	/**
	 * 空白用户id
	 */
	public static final String BLANK_USER_ID = "101131";

	/**
	 * 无效level
	 */
	public static final String INVALID_LEVEL = "101132";

	/**
	 * 无效type
	 */
	public static final String INVALID_TYPE = "101133";

	/**
	 * 无效常用资产
	 */
	public static final String INVALID_COMMON_DEVICE = "101134";

	/**
	 * 不满足复盘条件
	 */
	public static final String REPLAY_NOT_ENABLED = "101135";

	/**
	 * 复盘场景不存在，未开启过
	 */
	public static final String REPLAY_SCENE_NOT_EXIST = "101136";

	/**
	 * 请训练结束后查看
	 */
	public static final String PLZ_REPLAY_AFTER_TRAINING_ENDS = "101137";

	/**
	 * 请关闭训练场景后查看
	 */
	public static final String PLZ_REPLAY_AFTER_SCENE_IS_STOPPED = "101138";


	/** 训练手册内容过长 */
	public static final String INVALID_TRAINING_MANUAL_CONTENT = "101139";

	/** 场景切换中 */
	public static final String SCENE_SWITCHING = "101140";
	/**
	 * 模板删除或未授权
	 */
	public static final String TEMPLATE_DELETED_OR_NOT_PERMITTED = "101141";

	/**
	 * 态势回放未选择动作类型
	 */
	public static final String ACTION_TYPE_NOT_SELECTED = "101142";

	/**
	 * 未选择事件类型
	 */
	public static final String NO_EVENT_TYPE_SELECTED = "101143";

	/**
	 * 导出技战法失败
	 */
	public static final String FAILED_TO_EXPORT_EVAL_SCHEME_TEMPLATE = "101144";

	/**
	 * stage名称错误
	 */
	public static final String ERROR_STAGE_NAME = "101145";

	/**
	 * 任务导控步骤前序步骤未完成
	 */
	public static final String PREVIOUS_STEP_WAS_NOT_COMPLETED = "101146";

	private CrBusinessErrorCode() {}
}
