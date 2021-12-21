package com.cyberpeace.constant;

import java.util.HashMap;
import java.util.Map;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;

public interface BusinessErrorMsg {

	Map<String, String> CHINESE_MAP = new HashMap<String, String>() {

		{

			put(CrBusinessErrorCode.NOT_PATH, "找不到有效目录");
			put(CrBusinessErrorCode.NOT_MD_PATH, "没有找到md文件");
			put(CrBusinessErrorCode.ZIP_PATH_NOT_EXTRA, "文件解压失败");

			put(RtksBusinessErrorCode.UNABLE_DELETE_FRAME, "当前层级下存在岗位，无法删除");
			put(RtksBusinessErrorCode.UNABLE_ADD_FRAME, "已经绑定了岗位，无法新增层级");
			put(RtksBusinessErrorCode.UNABLE_DELETE_KNOWLEDGE, "无法删除知识点");
			put(RtksBusinessErrorCode.UNABLE_DELETE_SKILL, "无法删除技能点");

			put(ErrorCode.DUPLICATE_KEY, "已存在");
			put(ErrorCode.REMOTE_SERVICE_EXCEPTION, "远程服务异常");

		}
	};

	Map<String, String> ENGLISH_MAP = new HashMap<String, String>() {

		{
			put(CrBusinessErrorCode.NOT_PATH, "path not exist");
			put(CrBusinessErrorCode.NOT_MD_PATH, "md file not exist");
			put(CrBusinessErrorCode.ZIP_PATH_NOT_EXTRA, "file decompress failure");

			put(RtksBusinessErrorCode.UNABLE_DELETE_FRAME,
					"role already exist under the level, unable to delete level");
			put(RtksBusinessErrorCode.UNABLE_ADD_FRAME,
					"role already exist under the level, unable to add child level");
			put(RtksBusinessErrorCode.UNABLE_DELETE_KNOWLEDGE, "unable to delete knowledge");
			put(RtksBusinessErrorCode.UNABLE_DELETE_SKILL, "unable to delete skill");

			put(ErrorCode.DUPLICATE_KEY, " already exist");
			put(ErrorCode.REMOTE_SERVICE_EXCEPTION, "REMOTE_SERVICE_EXCEPTION");

		}
	};

}
