package com.cyberpeace.constant;

import java.util.HashMap;
import java.util.Map;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;

public interface BusinessErrorMsg {

	Map<String, String> CHINESE_MAP = new HashMap<String, String>() {

		{

			put(CrBusinessErrorCode.NOT_PATH, "找不到有效目录");
			put(CrBusinessErrorCode.NOT_MD_PATH, "没有找到md文件");
			put(CrBusinessErrorCode.ZIP_PATH_NOT_EXTRA, "文件解压失败");
			put(CrBusinessErrorCode.ROOM_NOT_EXIST, "训练不存在");
			put(CrBusinessErrorCode.PERMISSION_DENIED, "无权限操作");
			put(CrBusinessErrorCode.TRAIN_NOT_BEGIN, "训练未开始");
			put(CrBusinessErrorCode.TRAIN_IS_OVER, "训练已结束");
			put(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR, "训练角色不存在");
			put(CrBusinessErrorCode.TASK_NOT_EXIST_ERROR, "题目不存在");
			put(CrBusinessErrorCode.SCORE_RANGE_ERROR, "评分范围出错");
			put(CrBusinessErrorCode.RESOURCE_ID_MISSING, "没有获取到编号");
			put(CrBusinessErrorCode.MISSION_UNRELEASED, "任务未下发");
			put(CrBusinessErrorCode.CANNOT_SUBMIT_FINISHED_TASK, "任务已完成");
			put(CrBusinessErrorCode.MISSION_NOT_EXIST, "任务不存在");
			put(CrBusinessErrorCode.MISSION_SUBMIT_NOT_FOUND, "找不到指定的用户提交");
			put(CrBusinessErrorCode.TASK_REJECTED, "任务处于驳回状态");
			put(CrBusinessErrorCode.MISSION_ISSUED, "任务已下发");
			put(CrBusinessErrorCode.TEMPLATE_TYPE_NOT_SUPPORTED, "模板类型不支持");
			put(CrBusinessErrorCode.TEMPLATE_NAME_DUPLICATE, "模板名称已经存在");
			put(CrBusinessErrorCode.ROLE_NAME_DUPLICATE, "角色名称重复");
			put(CrBusinessErrorCode.SCENE_START_REPEAT, "场景重复启动");
			put(CrBusinessErrorCode.SAVE_LOCAL_DATA_FAILURE, "启动场景时保存本地数据错误");
			put(CrBusinessErrorCode.LOCAL_SCENE_NOT_EXIST, "场景不存在");
			put(CrBusinessErrorCode.FILE_FORMAT_ERROR, "文件格式错误");
			put(CrBusinessErrorCode.SCENE_NOT_STARTED, "场景未开启");

			put(CrBusinessErrorCode.EMPTY_RESOURCE_ID_LIST, "资源id列表为空");

			put(RtksBusinessErrorCode.UNABLE_DELETE_FRAME, "当前层级下存在岗位，无法删除");
			put(RtksBusinessErrorCode.UNABLE_ADD_FRAME, "已经绑定了岗位，无法新增层级");
			put(RtksBusinessErrorCode.UNABLE_DELETE_KNOWLEDGE, "无法删除知识点");
			put(RtksBusinessErrorCode.UNABLE_DELETE_SKILL, "无法删除技能点");

			put(ErrorCode.DUPLICATE_KEY, "已存在");
			put(ErrorCode.REMOTE_SERVICE_EXCEPTION, "远程服务异常");
			put(ErrorCode.FILE_TYPE_ERROR, "文件类型错误");
			put(ErrorCode.FILE_NOT_EXIST, "文件不存在");
			put(ErrorCode.INTERFACE_CURRENT_LIMITING, "接口访问太频繁，请稍后访问");

			put(CrBusinessErrorCode.TOOL_CATEGORY_IN_USE, "工具分类有关联的工具，不能删除");
			put(CrBusinessErrorCode.DUPLICATED_TOOL_NAME, "该工具名称与系统内已有名称重复，请重新输入");
			put(CrBusinessErrorCode.DUPLICATED_TOOL_CATEGORY_NAME, "该工具分类中文名称与系统内已有名称重复，请重新输入");

			put(CrBusinessErrorCode.FAILED_TO_RESTART_NODE, "重启节点失败");
			put(CrBusinessErrorCode.FAILED_TO_RECREATE_NODE, "重建节点失败");

			put(CrBusinessErrorCode.START_FLOW_FAILURE, "启动流量失败");
			put(CrBusinessErrorCode.STOP_FLOW_FAILURE, "停止流量失败");

			put(CrBusinessErrorCode.ALREADY_PAUSED, "已暂停状态不能执行操作");
			put(CrBusinessErrorCode.NOT_PAUSED, "非暂停状态不能执行恢复操作");

			put(CrBusinessErrorCode.INVALID_STAGE_NAME, "不合法的阶段名称长度");
			put(CrBusinessErrorCode.INVALID_MISSION_NAME, "不合法的任务名称长度");
			put(CrBusinessErrorCode.DUPLICATED_STAGE_NAME, "阶段名称重复");
			put(CrBusinessErrorCode.DUPLICATED_MISSION_NAME, "阶段内任务重名");
			put(CrBusinessErrorCode.SCORE_OUT_OF_RANGE, "分数超出范围");
			put(CrBusinessErrorCode.ANSWER_NOT_NULLABLE, "答案不可为空");
			put(CrBusinessErrorCode.OPTIONS_NOT_NULLABLE, "选项不可为空");
			put(CrBusinessErrorCode.OPTIONS_DUPLICATE, "选项重复");
			put(CrBusinessErrorCode.DUPLICATED_MISSION_SCHEME_TEMPLATE_TITLE, "任务方案名称重复");

			put(OjBusinessErrorCode.PRACTICE_COUNT_NOT_ENOUGH, "习题数量不足");
			put(OjBusinessErrorCode.CLASS_SCENE_IS_RUNNING, "课堂场景运行中");
			put(OjBusinessErrorCode.SCENE_IS_RUNNING, "场景运行中");
			put(OjBusinessErrorCode.RESOURCE_PERMISSION, "权限不足");
			put(OjBusinessErrorCode.COURSE_NUM_IS_EXIST, "课程编号已存在");
			put(OjBusinessErrorCode.NAME_IS_EXIST, "名称已存在");
			put(OjBusinessErrorCode.FILED_DATA_TOO_LONG, "某字段数据过长");
			put(OjBusinessErrorCode.SCENE_START_FAIL, "场景启动失败");
			put(OjBusinessErrorCode.END_GT_START_TIME, "结束时间必须大于开始时间");
			put(OjBusinessErrorCode.CLASSROOM_TIME_RANGE_ERROR, "课程表时间范围为上午8点-晚上21点");
			put(OjBusinessErrorCode.ORGANIZATION_IS_NO_USERS, "选择组织下无用户");
			put(OjBusinessErrorCode.RESOURCE_USED_NOT_DELETED, "资源正在被使用");
			put(OjBusinessErrorCode.CLASS_IN_SCHEDULES, "该班级在该时间段内已有课程安排");
			put(OjBusinessErrorCode.CLASS_LESSON_ORG_SAME, "已存在组织和课时相同的课程安排");

			put(CrBusinessErrorCode.SWITCH_RECORDING_FAILURE, "设置录屏开关失败");

			put(CrBusinessErrorCode.EMPTY_MISSION_TEMPLATE, "存在空任务模板");
			put(CrBusinessErrorCode.MISSION_TEMPLATE_INVALID, "无效任务模板");
			put(CrBusinessErrorCode.TRAINING_NAME_LEN_ERROR,
					String.format("训练名称长度超出范围，应为 %d ~ %d ", CrCommonConstants.MIN_TRAINING_NAME_LEN,
							CrCommonConstants.MAX_TRAINING_NAME_LEN));
			put(CrBusinessErrorCode.TRAINING_NAME_DUPLICATE, "已存在同名训练");
			put(CrBusinessErrorCode.TRAINING_NOT_RUNNING, "当前非训练时间");
			put(CrBusinessErrorCode.MACHINE_OCCUPIED, "机器已被占用");
			put(CrBusinessErrorCode.ROLE_USERS_NOT_BE_EMPTY, "角色成员不可为空");
			put(CrBusinessErrorCode.MISSION_LESS, "任务id重复或者部分任务缺失");
			put(CrBusinessErrorCode.DYNAMIC_MISSION_TITLE_DUPLICATE, "动态新增任务不可与角色既有任务重名");
			put(CrBusinessErrorCode.INVALID_RESOURCE_ZIP, "无效的资源压缩包");
			put(ErrorCode.FILE_COPY_FAIL, "文件复制失败");
			put(CrBusinessErrorCode.FAILED_TO_PAUSE_SCENE, "暂停场景失败");
			put(CrBusinessErrorCode.FAILED_TO_UNPAUSE_SCENE, "恢复场景失败");
			put(CrBusinessErrorCode.SINGLE_TRAIN_TEMPLATE_IN_USE, "单兵训练模板正在使用，删除失败");
			put(CrBusinessErrorCode.ROOM_NOT_EXIST_SUBMIT_MISSION, "请先启动实例");

			put(CrBusinessErrorCode.EMPTY_MISSION_STAGE, "阶段内任务不可为空");
			put(CrBusinessErrorCode.INVALID_TAGS_NUM, String.format("标签个数在 %d ~ %d 之间",
					CrCommonConstants.MIN_TAG_COUNT, CrCommonConstants.MAX_TAG_COUNT));
			put(CrBusinessErrorCode.ILLEGAL_TAG_LEN, "标签字符长度不正确");
			put(CrBusinessErrorCode.TAG_DUPLICATE, "标签内容不能重复");
			put(CrBusinessErrorCode.ILLEGAL_NAME_LEN, "名称长度非法");

		}
	};

	Map<String, String> ENGLISH_MAP = new HashMap<String, String>() {

		{
			put(CrBusinessErrorCode.NOT_PATH, "path not exist");
			put(CrBusinessErrorCode.NOT_MD_PATH, "md file not exist");
			put(CrBusinessErrorCode.ZIP_PATH_NOT_EXTRA, "file decompress failure");
			put(CrBusinessErrorCode.ROOM_NOT_EXIST, "training not exists");
			put(CrBusinessErrorCode.PERMISSION_DENIED, "permission denied");
			put(CrBusinessErrorCode.TRAIN_NOT_BEGIN, "training not begin");
			put(CrBusinessErrorCode.TRAIN_IS_OVER, "training is over");
			put(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR, "role not exist");
			put(CrBusinessErrorCode.TASK_NOT_EXIST_ERROR, "task not exist");
			put(CrBusinessErrorCode.SCORE_RANGE_ERROR, "score out of range");
			put(CrBusinessErrorCode.RESOURCE_ID_MISSING, "resource id missing");
			put(CrBusinessErrorCode.MISSION_UNRELEASED, "mission unreleased");
			put(CrBusinessErrorCode.CANNOT_SUBMIT_FINISHED_TASK, "cannot submit finished task");
			put(CrBusinessErrorCode.MISSION_NOT_EXIST, "mission not exist");
			put(CrBusinessErrorCode.MISSION_SUBMIT_NOT_FOUND, "mission submitter not found");
			put(CrBusinessErrorCode.TASK_REJECTED, "task is rejected");
			put(CrBusinessErrorCode.MISSION_ISSUED, "mission is issued");
			put(CrBusinessErrorCode.TEMPLATE_TYPE_NOT_SUPPORTED, "template type not supported");
			put(CrBusinessErrorCode.TEMPLATE_NAME_DUPLICATE, "template name duplicate");
			put(CrBusinessErrorCode.ROLE_NAME_DUPLICATE, "role name duplicate");
			put(CrBusinessErrorCode.SCENE_START_REPEAT, "scene start repeat");
			put(CrBusinessErrorCode.SAVE_LOCAL_DATA_FAILURE, "save local data failure");
			put(CrBusinessErrorCode.LOCAL_SCENE_NOT_EXIST, "local scene not exist");
			put(CrBusinessErrorCode.FILE_FORMAT_ERROR, "file format error");

			put(CrBusinessErrorCode.SCENE_NOT_STARTED, "Scene not started");

			put(RtksBusinessErrorCode.UNABLE_DELETE_FRAME,
					"role already exist under the level, unable to delete level");
			put(RtksBusinessErrorCode.UNABLE_ADD_FRAME,
					"role already exist under the level, unable to add child level");
			put(RtksBusinessErrorCode.UNABLE_DELETE_KNOWLEDGE, "unable to delete knowledge");
			put(RtksBusinessErrorCode.UNABLE_DELETE_SKILL, "unable to delete skill");

			put(ErrorCode.DUPLICATE_KEY, "already exist.");
			put(ErrorCode.REMOTE_SERVICE_EXCEPTION, "Remote service error.");
			put(ErrorCode.FILE_TYPE_ERROR, "File type error.");
			put(ErrorCode.FILE_NOT_EXIST, "File not exist.");
			put(ErrorCode.INTERFACE_CURRENT_LIMITING, "interface access is too frequent. please visit later");

			put(CrBusinessErrorCode.TOOL_CATEGORY_IN_USE,
					"unable to delete category, category in use");
			put(CrBusinessErrorCode.DUPLICATED_TOOL_NAME,
					"duplicated tool name in system, re-enter");
			put(CrBusinessErrorCode.DUPLICATED_TOOL_CATEGORY_NAME,
					"duplicated tool category name in system, re-enter");

			put(CrBusinessErrorCode.FAILED_TO_RESTART_NODE, "Failed to restart node.");
			put(CrBusinessErrorCode.FAILED_TO_RECREATE_NODE, "Failed to recreate node.");

			put(CrBusinessErrorCode.START_FLOW_FAILURE, "Failed to start flow");
			put(CrBusinessErrorCode.STOP_FLOW_FAILURE, "Failed to stop flow");

			put(CrBusinessErrorCode.ALREADY_PAUSED, "Scene is already paused.");
			put(CrBusinessErrorCode.NOT_PAUSED, "Scene is not paused");

			put(CrBusinessErrorCode.INVALID_STAGE_NAME, "Invalid stage name.");
			put(CrBusinessErrorCode.INVALID_MISSION_NAME, "Invalid mission name.");
			put(CrBusinessErrorCode.DUPLICATED_STAGE_NAME, "Duplicated stage names.");
			put(CrBusinessErrorCode.DUPLICATED_MISSION_NAME,
					"Duplicated mission names in one stage.");
			put(CrBusinessErrorCode.DUPLICATED_MISSION_SCHEME_TEMPLATE_TITLE, "Duplicated title.");

			put(OjBusinessErrorCode.PRACTICE_COUNT_NOT_ENOUGH,
					"The number of practice sets is insufficient.");
			put(OjBusinessErrorCode.CLASS_SCENE_IS_RUNNING, "Class Scene is running.");
			put(OjBusinessErrorCode.RESOURCE_PERMISSION, "Resource not permission.");
			put(OjBusinessErrorCode.COURSE_NUM_IS_EXIST, "Course number already exists.");
			put(OjBusinessErrorCode.NAME_IS_EXIST, "Name already exists.");
			put(OjBusinessErrorCode.FILED_DATA_TOO_LONG, "The field data is too long.");
			put(OjBusinessErrorCode.SCENE_START_FAIL, "Scene startup failure.");
			put(OjBusinessErrorCode.END_GT_START_TIME,
					"The end time must be greater than the start time.");
			put(OjBusinessErrorCode.CLASSROOM_TIME_RANGE_ERROR,
					"Time is out of range, only 8 to 21.");
			put(OjBusinessErrorCode.ORGANIZATION_IS_NO_USERS,
					"Select No users in the organization.");
			put(OjBusinessErrorCode.RESOURCE_USED_NOT_DELETED, "Resource is being used.");
			put(OjBusinessErrorCode.CLASS_IN_SCHEDULES, "The class has courses during this time.");
			put(OjBusinessErrorCode.CLASS_LESSON_ORG_SAME,
					"A course schedule with the same organization and lesson already exists.");
			put(OjBusinessErrorCode.SCENE_IS_RUNNING, "Scene is running.");

			put(CrBusinessErrorCode.SCORE_OUT_OF_RANGE, "Score out of range.");
			put(CrBusinessErrorCode.ANSWER_NOT_NULLABLE, "Answer cannot be blank.");
			put(CrBusinessErrorCode.OPTIONS_NOT_NULLABLE, "Options cannot be blank.");
			put(CrBusinessErrorCode.OPTIONS_DUPLICATE, "Options duplicate.");
			put(CrBusinessErrorCode.SWITCH_RECORDING_FAILURE, "Failed to switch recording.");
			put(CrBusinessErrorCode.TRAINING_NAME_LEN_ERROR,
					String.format("Name length out of range, supposed to be between %d and %d.",
							CrCommonConstants.MIN_TRAINING_NAME_LEN,
							CrCommonConstants.MAX_TRAINING_NAME_LEN));
			put(CrBusinessErrorCode.TRAINING_NAME_DUPLICATE, "Training name duplicate");

			put(CrBusinessErrorCode.EMPTY_MISSION_TEMPLATE, "Mission Template is empty.");
			put(CrBusinessErrorCode.MISSION_TEMPLATE_INVALID, "Mission Template is invalid.");
			put(CrBusinessErrorCode.ROLE_USERS_NOT_BE_EMPTY, "role users cannot be empty");
			put(CrBusinessErrorCode.TRAINING_NOT_RUNNING, "Training is not running right now.");

			put(CrBusinessErrorCode.MACHINE_OCCUPIED, "Machine occupied.");
			put(CrBusinessErrorCode.DYNAMIC_MISSION_TITLE_DUPLICATE,
					"Mission title duplicates with existed missions.");
			put(CrBusinessErrorCode.MISSION_LESS, "Duplicate or not-found template_question id");

			put(CrBusinessErrorCode.EMPTY_RESOURCE_ID_LIST, "Resource id list is empty.");
			put(CrBusinessErrorCode.INVALID_RESOURCE_ZIP, "Invalid resource zip.");
			put(ErrorCode.FILE_COPY_FAIL, "File copy failure.");

			put(CrBusinessErrorCode.FAILED_TO_UNPAUSE_SCENE, "Failed to unpause scene.");
			put(CrBusinessErrorCode.FAILED_TO_PAUSE_SCENE, "Failed to pause scene.");
			put(CrBusinessErrorCode.SINGLE_TRAIN_TEMPLATE_IN_USE,
					"Failed to delete single training template in use.");
			put(CrBusinessErrorCode.ROOM_NOT_EXIST_SUBMIT_MISSION, "Please Start Up Case.");

			put(CrBusinessErrorCode.EMPTY_MISSION_STAGE, "Missions of one stage cant be empty.");
			put(CrBusinessErrorCode.INVALID_TAGS_NUM,
					String.format("Number of tags is supposed to be between %d ~ %d.",
							CrCommonConstants.MIN_TAG_COUNT, CrCommonConstants.MAX_TAG_COUNT));
			put(CrBusinessErrorCode.ILLEGAL_TAG_LEN, "Illegal length for tag.");
			put(CrBusinessErrorCode.TAG_DUPLICATE, "Tags duplicate.");
			put(CrBusinessErrorCode.ILLEGAL_NAME_LEN, "Illegal length for name.");
		}
	};

}
