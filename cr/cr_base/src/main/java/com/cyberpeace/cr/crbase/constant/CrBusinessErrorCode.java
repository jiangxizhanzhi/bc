package com.cyberpeace.cr.crbase.constant;

/**
 * 靶场自定义错误码。
 * 1、从101001开始递增编号；
 * 2、截止编号地址101999；
 * <p>
 * 编号定义完成后，需要在wiki填写编号对应的错误原因。
 * wiki地址：http://10.10.10.11/pages/viewpage.action?pageId=27367377
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

}
