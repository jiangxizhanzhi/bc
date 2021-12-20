package com.cyberpeace.constant;

import java.util.HashMap;
import java.util.Map;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
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

			put(RtksBusinessErrorCode.UNABLE_DELETE_FRAME, "当前层级下存在岗位，无法删除");
			put(RtksBusinessErrorCode.UNABLE_ADD_FRAME, "已经绑定了岗位，无法新增层级");
			put(RtksBusinessErrorCode.UNABLE_DELETE_KNOWLEDGE, "无法删除知识点");
			put(RtksBusinessErrorCode.UNABLE_DELETE_SKILL, "无法删除技能点");

			put(ErrorCode.DUPLICATE_KEY, "已存在");
			put(ErrorCode.REMOTE_SERVICE_EXCEPTION, "远程服务异常");

			put(CrBusinessErrorCode.TOOL_CATEGORY_IN_USE, "工具分类有关联的工具，不能删除");

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

			put(RtksBusinessErrorCode.UNABLE_DELETE_FRAME,
					"role already exist under the level, unable to delete level");
			put(RtksBusinessErrorCode.UNABLE_ADD_FRAME,
					"role already exist under the level, unable to add child level");
			put(RtksBusinessErrorCode.UNABLE_DELETE_KNOWLEDGE, "unable to delete knowledge");
			put(RtksBusinessErrorCode.UNABLE_DELETE_SKILL, "unable to delete skill");

			put(ErrorCode.DUPLICATE_KEY, " already exist");
			put(ErrorCode.REMOTE_SERVICE_EXCEPTION, "REMOTE_SERVICE_EXCEPTION");

			put(CrBusinessErrorCode.TOOL_CATEGORY_IN_USE,
					"unable to delete category, category in use");
		}
	};

}
