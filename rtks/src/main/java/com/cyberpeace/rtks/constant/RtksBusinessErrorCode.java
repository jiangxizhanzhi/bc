package com.cyberpeace.rtks.constant;

import com.cyberpeace.constant.BaseI18nErrorMsg;
import lombok.Getter;

/**
 * 靶场自定义错误码。
 * 1、从100001开始递增编号；
 * 2、截止编号地址100999；
 * 实际100011开始的在ErrorCode中
 * <p>
 * 编号定义完成后，需要在wiki填写编号对应的错误原因。
 * wiki地址：http://10.10.10.11/pages/viewpage.action?pageId=27367377
 */
public enum RtksBusinessErrorCode implements BaseI18nErrorMsg {

	/**
	 * RTKS框架管理中，当前层级下存在角色的时候，用户试图删除该层级
	 */
	UNABLE_DELETE_FRAME("100001", "当前层级下存在岗位，无法删除", "role already exist under the level, unable to delete level"),

	/**
	 * 已经绑定了岗位的层级不允许新增子层级
	 */
	UNABLE_ADD_FRAME("100002", "已经绑定了岗位，无法新增层级", "role already exist under the level, unable to add child level"),

	/**
	 * 已经绑定了知识点，无法删除
	 */
	UNABLE_DELETE_KNOWLEDGE("100003", "无法删除知识点", "unable to delete knowledge"),

	/**
	 * 已经绑定了技能点，无法删除
	 */
	UNABLE_DELETE_SKILL("100004", "无法删除技能点", "unable to delete skill");


	@Getter
	private final String chinese;

	@Getter
	private final String english;

	@Getter
	private final String errorCode;

	RtksBusinessErrorCode(String errorCode, String chinese, String english) {
		this.errorCode = errorCode;
		this.chinese = chinese;
		this.english = english;
	}
}
