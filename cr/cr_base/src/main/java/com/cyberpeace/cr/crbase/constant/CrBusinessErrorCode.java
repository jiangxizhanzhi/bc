package com.cyberpeace.cr.crbase.constant;

import com.cyberpeace.constant.BaseI18nErrorMsg;
import lombok.Getter;

/**
 * 靶场自定义错误码。
 * 1、从101001开始递增编号；
 * 2、截止编号地址101999；
 * <p>
 * 编号定义完成后，需要在wiki填写编号对应的错误原因。
 * wiki地址：http://10.10.10.11/pages/viewpage.action?pageId=27367377
 */
public enum CrBusinessErrorCode implements BaseI18nErrorMsg {

	// 找不到有效目录
	NOT_PATH("101001", "找不到有效目录", "path not exist"),

	// 没有找到md文件
	NOT_MD_PATH("101002", "没有找到md文件", "md file not exist"),

	// 文件解压失败
	ZIP_PATH_NOT_EXTRA("101003", "文件解压失败", "file decompress failure"),

	// 实例不存在
	ROOM_NOT_EXIST("101004", "训练不存在", "training not exists"),

	// 无权限操作
	PERMISSION_DENIED("101005", "无权限操作", "permission denied"),

	// 训练未开始
	TRAIN_NOT_BEGIN("101006", "训练未开始", "training not begin"),

	// 训练已结束
	TRAIN_IS_OVER("101007", "训练已结束", "training is over"),

	// 训练角色不存在
	ROLE_NOT_EXIST_ERROR("101008", "训练角色不存在", "role not exist"),

	// 任务（Task）不存在
	TASK_NOT_EXIST_ERROR("101009", "题目不存在", "task not exist"),

	// 评分范围出错
	SCORE_RANGE_ERROR("101010", "评分范围出错", "score out of range"),

	// 更新实例时没有传入resource_id字段
	RESOURCE_ID_MISSING("101011", "没有获取到编号", "resource id missing"),

	// 任务未下发，不能提交答案
	MISSION_UNRELEASED("101012", "任务未下发", "mission unreleased"),

	// 任务处于完成状态，不可再次提交答案
	CANNOT_SUBMIT_FINISHED_TASK("101013", "任务已完成", "cannot submit finished task"),

	// 指定任务不存在
	MISSION_NOT_EXIST("101014", "任务不存在", "mission not exist"),

	// 找不到指定的用户提交
	MISSION_SUBMIT_NOT_FOUND("101015", "找不到指定的用户提交", "mission submitter not found"),

	// 任务处于驳回状态不可评分
	TASK_REJECTED("101016", "任务处于驳回状态", "task is rejected"),

	// 任务已下发
	MISSION_ISSUED("101017", "任务已下发", "mission is issued"),

	// 模板类型不支持
	TEMPLATE_TYPE_NOT_SUPPORTED("101018", "模板类型不支持", "template type not supported"),

	// 模板名称已经存在
	TEMPLATE_NAME_DUPLICATE("101019", "模板名称已经存在", "template name duplicate"),

	// 角色名称重复
	ROLE_NAME_DUPLICATE("101020", "角色名称重复", "role name duplicate"),

	// 场景重复启动
	SCENE_START_REPEAT("101021", "场景重复启动", "scene start repeat"),

	// 场景不存在
	LOCAL_SCENE_NOT_EXIST("101022", "场景不存在", "local scene not exist"),

	// 启动场景时保存本地数据错误
	SAVE_LOCAL_DATA_FAILURE("101023", "启动场景时保存本地数据错误", "save local data failure"),

	// 文件格式错误
	FILE_FORMAT_ERROR("101024", "文件格式错误", "file format error"),

	// 工具库分类在使用时不能删除
	TOOL_CATEGORY_IN_USE("101025", "工具分类有关联的工具，不能删除", "unable to delete category, category in use"),

	// 指定模板不存在
	TEMPLATE_NOT_EXIST("101026", "指定模板不存在", ""),

	// 场景未开启
	SCENE_NOT_STARTED("101027", "场景未开启", "Scene not started"),

	// 资源id列表为空
	EMPTY_RESOURCE_ID_LIST("101028", "资源id列表为空", "Resource id list is empty."),

	// 启动流量失败
	START_FLOW_FAILURE("101029", "启动流量失败", "Failed to start flow"),

	// 训练已开始
	TRAIN_HAS_BEGUN("101030", "", ""),

	// 训练速率范围超限
	RATE_VALUE_RANGE_ERROR("101031", "训练速率范围超限", ""),

	// 重启节点失败
	FAILED_TO_RESTART_NODE("101032", "重启节点失败", "Failed to restart node."),

	// 重建节点失败
	FAILED_TO_RECREATE_NODE("101033", "重建节点失败", "Failed to recreate node."),

	// 场景已暂停，不能执行暂停操作
	ALREADY_PAUSED("101034", "已暂停状态不能执行操作", "Scene is already paused."),

	// 非暂停状态不能执行恢复操作
	NOT_PAUSED("101035", "非暂停状态不能执行恢复操作", "Scene is not paused"), // todo

	// 停止流量失败
	STOP_FLOW_FAILURE("101036", "停止流量失败", "Failed to stop flow"),

	// 非法的阶段名长度
	INVALID_STAGE_NAME("101037", "不合法的阶段名称长度", "Invalid stage name."),

	// 非法的任务名称长度
	INVALID_MISSION_NAME("101038", "不合法的任务名称长度", "Invalid mission name."),

	// 任务方案阶段名称重复
	DUPLICATED_STAGE_NAME("101039", "阶段名称重复", "Duplicated stage names."),

	// 阶段内任务名称重复
	DUPLICATED_MISSION_NAME("101040", "阶段内任务重名", "Duplicated mission names in one stage."),

	// 分数超出范围
	SCORE_OUT_OF_RANGE("101041", "分数超出范围", "Score out of range."),

	// 答案不可为空
	ANSWER_NOT_NULLABLE("101042", "答案不可为空", "Answer cannot be blank."),

	// 选项不可为空
	OPTIONS_NOT_NULLABLE("101043", "选项不可为空", "Options cannot be blank."),

	// 选项重复
	OPTIONS_DUPLICATE("101044", "选项重复", "Options duplicate."),

	// 录屏开关设置失败
	SWITCH_RECORDING_FAILURE("101045", "设置录屏开关失败", "Failed to switch recording."),

	// 存在空任务模板
	EMPTY_MISSION_TEMPLATE("101046", "存在空任务模板", "Mission Template is empty."),

	// 无效任务模板
	MISSION_TEMPLATE_INVALID("101047", "无效任务模板", "Mission Template is invalid."),

	// 训练名称长度超出范围，应为4~40
	TRAINING_NAME_LEN_ERROR("101048", "训练名称长度超出范围，应为 %d ~ %d ", "Name length out of range, supposed to be between %d and %d."),

	// 同名训练已存在
	TRAINING_NAME_DUPLICATE("101049", "已存在同名训练", "Training name duplicate"),

	// 当前非训练时间
	TRAINING_NOT_RUNNING("101050", "当前非训练时间", "Training is not running right now."),

	// 主机已被占用
	MACHINE_OCCUPIED("101051", "机器已被占用", "Machine occupied."),

	// 角色成员不可为空
	ROLE_USERS_NOT_BE_EMPTY("101052", "角色成员不可为空", "role users cannot be empty"),

	// 任务id重复或者部分任务缺失
	MISSION_LESS("101053", "任务id重复或者部分任务缺失", "Duplicate or not-found template_question id"),

	// 动态新增任务不可与角色既有任务重名
	DYNAMIC_MISSION_TITLE_DUPLICATE("101054", "动态新增任务不可与角色既有任务重名", "Mission title duplicates with existed missions."),

	// 资源包错误
	INVALID_RESOURCE_ZIP("101055", "无效的资源压缩包", "Invalid resource zip."),

	// 工具名称与系统内已有名称重复
	DUPLICATED_TOOL_NAME("101056", "该工具名称与系统内已有名称重复，请重新输入", "duplicated tool name in system, re-enter"),

	// 工具分类名称与系统内已有名称重复
	DUPLICATED_TOOL_CATEGORY_NAME("101057", "该工具分类中文名称与系统内已有名称重复，请重新输入", "duplicated tool category name in system, re-enter"),

	// 任务方案名称已存在
	DUPLICATED_MISSION_SCHEME_TEMPLATE_TITLE("101058", "任务方案名称重复", "Duplicated title."),

	// 暂停场景失败
	FAILED_TO_PAUSE_SCENE("101059", "暂停场景失败", "Failed to pause scene."),

	// 恢复场景失败
	FAILED_TO_UNPAUSE_SCENE("101060", "恢复场景失败", "Failed to unpause scene."),

	// 单兵训练模板正在使用，删除失败
	SINGLE_TRAIN_TEMPLATE_IN_USE("101061", "单兵训练模板正在使用，删除失败", "Failed to delete single training template in use."),

	// 空房间时提交任务
	ROOM_NOT_EXIST_SUBMIT_MISSION("101062", "请先启动实例", "Please Start Up Case."),

	// 阶段内任务不可为空
	EMPTY_MISSION_STAGE("101063", "阶段内任务不可为空", "Missions of one stage cant be empty."),

	// 标签个数在1~4之间
	INVALID_TAGS_NUM("101064", "标签个数在 %d ~ %d 之间", "Number of tags is supposed to be between %d ~ %d."),

	// 标签字符长度不正确
	ILLEGAL_TAG_LEN("101065", "标签字符长度不正确", "Illegal length for tag."),

	// 标签内容不能重复
	TAG_DUPLICATE("101066", "标签内容不能重复", "Tags duplicate."),

	// 名称长度非法
	ILLEGAL_NAME_LEN("101067", "名称长度非法", "Illegal length for name."),

	INVALID_SKILL_POINT_VALUE("", "", "illegal skill point value, 1~5 expected");


	@Getter
	private final String chinese;

	@Getter
	private final String english;

	@Getter
	private final String errorCode;

	CrBusinessErrorCode(String errorCode, String chinese, String english) {
		this.errorCode = errorCode;
		this.chinese = chinese;
		this.english = english;
	}
}
