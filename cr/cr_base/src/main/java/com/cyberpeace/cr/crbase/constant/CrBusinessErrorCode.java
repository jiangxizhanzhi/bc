package com.cyberpeace.cr.crbase.constant;

/**
 * 靶场自定义错误码。
 * 1、从101001开始递增编号；
 * 2、截止编号地址101999；
 *
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
	String TEMPLETE_TYPE_NOT_SUPPORTED = "101018";

	// 模板名称已经存在
	String TEMPLETE_NAME_EXIST_ERROR = "101019";

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
}
