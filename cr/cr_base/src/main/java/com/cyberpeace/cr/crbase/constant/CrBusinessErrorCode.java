package com.cyberpeace.cr.crbase.constant;

/**
 * cr业务错误代码
 * 靶场自定义错误码。
 * 1、从101001开始递增编号；
 * 2、截止编号地址101999；
 * <p>
 * 编号定义完成后，需要在wiki填写编号对应的错误原因。
 * wiki地址：http://10.10.10.11/pages/viewpage.action?pageId=27367377
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public interface CrBusinessErrorCode {

	// 找不到有效目录
	String NOT_PATH = "101001";

	// 没有找到md文件
	String NOT_MD_PATH = "101002";

	// 文件解压失败
	String ZIP_PATH_NOT_EXTRA = "101003";

	// 实例不存在
	String ROOM_NOT_EXIST = "101004";

	// 无权限操作
	String PERMISSION_DENIED = "101005";

	// 训练未开始
	String TRAIN_NOT_BEGIN = "101006";

	// 训练已结束
	String TRAIN_IS_OVER = "101007";

	// 训练角色不存在
	String ROLE_NOT_EXIST_ERROR = "101008";

	// 任务（Task）不存在
	String TASK_NOT_EXIST_ERROR = "101009";

	// 评分范围出错
	String SCORE_RANGE_ERROR = "101010";

	// 更新实例时没有传入resource_id字段
	String RESOURCE_ID_MISSING = "101011";

	// 任务未下发，不能提交答案
	String MISSION_UNRELEASED = "101012";

	// 任务处于完成状态，不可再次提交答案
	String CANNOT_SUBMIT_FINISHED_TASK = "101013";

	// 指定任务不存在
	String MISSION_NOT_EXIST = "101014";

	// 找不到指定的用户提交
	String MISSION_SUBMIT_NOT_FOUND = "101015";

	// 任务处于驳回状态不可评分
	String TASK_REJECTED = "101016";

	// 任务已下发
	String MISSION_ISSUED = "101017";

	// 模板类型不支持
	String TEMPLATE_TYPE_NOT_SUPPORTED = "101018";

	// 模板名称已经存在
	String TEMPLATE_NAME_DUPLICATE = "101019";

	// 角色名称重复
	String ROLE_NAME_DUPLICATE = "101020";

	// 场景重复启动
	String SCENE_START_REPEAT = "101021";

	// 场景不存在
	String LOCAL_SCENE_NOT_EXIST = "101022";

	// 启动场景时保存本地数据错误
	String SAVE_LOCAL_DATA_FAILURE = "101023";

	// 文件格式错误
	String FILE_FORMAT_ERROR = "101024";

	// 工具库分类在使用时不能删除
	String TOOL_CATEGORY_IN_USE = "101025";

	// 指定模板不存在
	String TEMPLATE_NOT_EXIST = "101026";

	// 场景未开启
	String SCENE_NOT_STARTED = "101027";

	// 资源id列表为空
	String EMPTY_RESOURCE_ID_LIST = "101028";

	// 启动流量失败
	String START_FLOW_FAILURE = "101029";

	// 训练已开始
	String TRAIN_HAS_BEGUN = "101030";

	// 训练速率范围超限
	String RATE_VALUE_RANGE_ERROR = "101031";

	// 重启节点失败
	String FAILED_TO_RESTART_NODE = "101032";

	// 重建节点失败
	String FAILED_TO_RECREATE_NODE = "101033";

	// 场景已暂停，不能执行暂停操作
	String ALREADY_PAUSED = "101034";

	// 非暂停状态不能执行恢复操作
	String NOT_PAUSED = "101035";

	// 停止流量失败
	String STOP_FLOW_FAILURE = "101036";

	// 非法的阶段名长度
	String INVALID_STAGE_NAME = "101037";

	// 非法的任务名称长度
	String INVALID_MISSION_NAME = "101038";

	// 任务方案阶段名称重复
	String DUPLICATED_STAGE_NAME = "101039";

	// 阶段内任务名称重复
	String DUPLICATED_MISSION_NAME = "101040";

	// 分数超出范围
	String SCORE_OUT_OF_RANGE = "101041";

	// 答案不可为空
	String ANSWER_NOT_NULLABLE = "101042";

	// 选项不可为空
	String OPTIONS_NOT_NULLABLE = "101043";

	// 选项重复
	String OPTIONS_DUPLICATE = "101044";

	// 录屏开关设置失败
	String SWITCH_RECORDING_FAILURE = "101045";

	// 存在空任务模板
	String EMPTY_MISSION_TEMPLATE = "101046";

	// 无效任务模板
	String MISSION_TEMPLATE_INVALID = "101047";

	// 训练名称长度超出范围，应为4~40
	String TRAINING_NAME_LEN_ERROR = "101048";

	// 同名训练已存在
	String TRAINING_NAME_DUPLICATE = "101049";

	// 当前非训练时间
	String TRAINING_NOT_RUNNING = "101050";

	// 主机已被占用
	String MACHINE_OCCUPIED = "101051";

	// 角色成员不可为空
	String ROLE_USERS_NOT_BE_EMPTY = "101052";

	// 任务id重复或者部分任务缺失
	String MISSION_LESS = "101053";

	// 动态新增任务不可与角色既有任务重名
	String DYNAMIC_MISSION_TITLE_DUPLICATE = "101054";

	// 资源包错误
	String INVALID_RESOURCE_ZIP = "101055";

	// 工具名称与系统内已有名称重复
	String DUPLICATED_TOOL_NAME = "101056";

	// 工具分类名称与系统内已有名称重复
	String DUPLICATED_TOOL_CATEGORY_NAME = "101057";

	// 任务方案名称已存在
	String DUPLICATED_MISSION_SCHEME_TEMPLATE_TITLE = "101058";

	// 暂停场景失败
	String FAILED_TO_PAUSE_SCENE = "101059";

	// 恢复场景失败
	String FAILED_TO_UNPAUSE_SCENE = "101060";

	// 单兵训练模板正在使用，删除失败
	String SINGLE_TRAIN_TEMPLATE_IN_USE = "101061";

	// 空房间时提交任务
	String ROOM_NOT_EXIST_SUBMIT_MISSION = "101062";

	// 阶段内任务不可为空
	String EMPTY_MISSION_STAGE = "101063";

	// 标签个数在1~4之间
	String INVALID_TAGS_NUM = "101064";

	// 标签字符长度不正确
	String ILLEGAL_TAG_LEN = "101065";

	// 标签内容不能重复
	String TAG_DUPLICATE = "101066";

	// 名称长度非法
	String ILLEGAL_NAME_LEN = "101067";

	// 非法的技能点数值
	String INVALID_SKILL_POINT_VALUE = "101068";

	// 非法的技能点个数
	String INVALID_SKILL_POINTS_SIZE = "101069";

	/** 打分必须为正整数 */
	String INVALID_SCORE = "101070";

	/** 只有超级管理员可以修改雷达图 */
	String SUPERUSER_CAN_EDIT_RADAR = "101071";

	/** 非法的标题长度 */
	String ILLEGAL_TITLE_LEN = "101072";

	/** 非法的描述长度 */
	String ILLEGAL_DESC_LEN = "101073";

	/** 导出模板失败 */
	String FAILED_TO_DUMP_TEMPLATE = "101074";

	/** 导出任务方案失败 */
	String FAILED_TO_DUMP_COMBAT_TECH = "101075";

	/** 非法的难度 */
	String INVALID_DIFFICULTY = "101076";

	/** 非法的训练类型 */
	String INVALID_TRAINING_CATEGORY = "101077";

	/** 无效的角色配置信息 */
	String INVALID_ROLE_INFO = "101078";

	/** 非法的任务描述长度 */
	String ILLEGAL_MISSION_DESC_LEN = "101079";

	/** 训练已暂停，不能删除场景 */
	String TRAINING_PAUSED = "101080";

	/** 指标项已经使用 */
	String INDICATOR_ALREADY_USED = "101081";

	/** LOGO文件不存在 */
	String LOGO_NOT_EXIST = "101082";

	/** 测评靶场类型不存在 */
	String EVAL_CATEGORY_NOT_EXIST = "101083";

	/** 测评靶场存在运行任务 */
	String RUNNING_EVAL_TASK_EXIST = "101084";

	/** 测评靶场任务不在运行 */
	String EVAL_TASK_NOT_RUNNING = "101085";

	/** 测评靶场任务不存在 */
	String EVAL_TASK_NOT_EXIST = "101086";

	/** 测评靶场步骤不存在 */
	String EVAL_STEP_NOT_EXIST = "101087";

	/** 测评靶场步骤配置错误 */
	String EVAL_STEP_ILLEGAL = "101088";

	/** 测评靶场步骤未启动 */
	String EVAL_STEP_NOT_STARTED = "101089";

	/** 测评靶场步骤已启动 */
	String EVAL_STEP_IS_STARTED = "101090";

	/** 测评靶场步骤已结束 */
	String EVAL_STEP_IS_FINISHED = "101091";

	/** 测评靶场步骤前序步骤未完成 */
	String PRECEDING_STEP_NOT_FINISHED = "101092";

	/** 算法参数重名 */
	String PARAM_NAME_DUPLICATE = "101093";

	/** 未实现的方法 */
	String NOT_IMPLEMENTED_EXCEPTION = "101094";

	/**
	 * 变量项格式化算法执行失败
	 */
	String VARIABLE_FORMAT_EXECUTE_FAILED = "101095";

	/**
	 * 变量项匹配算法执行失败
	 */
	String VARIABLE_MATCH_EXECUTE_FAILED = "101096";

	/**
	 * 变量项评分算法执行失败
	 */
	String VARIABLE_MARK_EXECUTE_FAILED = "101097";

	/**
	 * 指标项执行失败
	 */
	String INDEX_EXECUTE_FAILED = "101098";

	/**
	 * 三级指标项数据无效
	 */
	String THIRD_INDEX_IS_FAILED = "101099";

	/**
	 * 指标体系变量项数据无效
	 */
	String SYSTEM_INDEX_IS_FAILED = "101100";

	/**
	 * 模型检测算法返回数据不合规
	 */
	String MATCH_ALGORITHM_RESULT_IS_INVALID = "101101";

	/**
	 * 采集变量项未获取到相关数据
	 */
	String VARIABLE_DID_NOT_GET_PROBE_DATA = "101102";

	/** 未指定节点 */
	String NOT_SPECIFY_NODE = "101103";

	/** 指定节点下无流量数据 */
	String NO_FLOW_EXISTS_ON_NODE = "101104";

	/** 流量不存在 */
	String FLOW_NOT_EXIST = "101105";

	/** 流量修改错误 */
	String FAILED_TO_UPDATE_FLOW = "101106";

	/**
	 * 靶场测评评估访问中台失败
	 */
	String GET_PROBE_DATA_FAILED = "101107";

	/**
	 * 当前节点存在未完成的流量任务
	 */
	String FLOW_TASK_EXISTS = "101108";

	/**
	 * 无法判断流量任务状态
	 */
	String FAILED_TO_JUDGE_FLOW_TASK_STATUS = "101109";

	/**
	 * 格式化算法返回数据不合规
	 */
	String FORMAT_ALGORITHM_RESULT_IS_INVALID = "101110";

	/**
	 * 评分算法返回数据不合规
	 */
	String MARK_ALGORITHM_RESULT_IS_INVALID = "101111";

	/**
	 * 聚合算法返回数据不合规
	 */
	String AGG_ALGORITHM_RESULT_IS_INVALID = "101112";

	/** 二层框架已满 */
	String SECOND_FLOOR_FRAME_IS_FULL = "101113";

	/**
	 * 标题重复
	 */
	String TITLE_DUPLICATE = "101114";

	/** 非背景流量不允许修改源IP */
	String NON_BACKGROUND_FLOW_NOT_ALLOWED_TO_MODIFY_SOURCE_IP = "101115";

	/** IP格式错误 */
	String WRONG_IP_FORMAT = "101116";

	/** 最大支持7个分类 */
	String MAX_7_CATEGORIES = "101117";

	/** 训练资管导出类型不正确 */
	String EXPORT_TRAIN_ERROR_TYPE = "101118";

	/** 训练资管导出id都不存在 */
	String EXPORT_TRAIN_ID_NOT_EXIST = "101119";

	/** 训练资管导出时当前训练下存在重名任务名称 */
	String EXPORT_TRAIN_DUPLICATED_TASK_TITLE = "101120";

	/** 动态修改任务不可与角色既有任务重名 */
	String DUPLICATE_DYNAMIC_MISSION_TITLE = "101121";

	/** 请指定一个学员 */
	String PLZ_SPECIFY_ONE_STUDENT = "101122";

}
