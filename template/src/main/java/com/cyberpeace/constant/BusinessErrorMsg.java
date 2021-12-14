package com.cyberpeace.constant;

import java.util.HashMap;
import java.util.Map;

import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;

public interface BusinessErrorMsg {

	Map<String, String> CHINESE_MAP = new HashMap<String, String>() {

		{
			put(RtksBusinessErrorCode.UNABLE_DELETE_FRAME, "当前层级下存在岗位，无法删除");
			put(RtksBusinessErrorCode.UNABLE_ADD_FRAME, "已经绑定了岗位，无法新增层级");
			put(RtksBusinessErrorCode.UNABLE_DELETE_KNOWLEDGE, "无法删除知识点");
			put(RtksBusinessErrorCode.UNABLE_DELETE_SKILL, "无法删除技能点");
			put(ErrorCode.DUPLICATE_KEY, "已存在");

		}
	};

	Map<String, String> ENGLISH_MAP = new HashMap<String, String>() {

		{
			put(RtksBusinessErrorCode.UNABLE_DELETE_FRAME,
					"role already exist under the level, unable to delete level");
			put(RtksBusinessErrorCode.UNABLE_ADD_FRAME,
					"role already exist under the level, unable to add child level");
			put(RtksBusinessErrorCode.UNABLE_DELETE_KNOWLEDGE, "unable to delete knowledge");
			put(RtksBusinessErrorCode.UNABLE_DELETE_SKILL, "unable to delete skill");
			put(ErrorCode.DUPLICATE_KEY, " already exist");

		}
	};

}
