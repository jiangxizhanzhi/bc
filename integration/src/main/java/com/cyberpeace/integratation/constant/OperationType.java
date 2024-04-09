package com.cyberpeace.integratation.constant;

import com.cyberpeace.constant.BaseI18n;

import lombok.Getter;

public enum OperationType implements BaseI18n {
	/**
	 * 操作类型
	 */

	LOGIN(0, "登入", "Log in"),
	SIGN_OUT(1, "登出", "Sign out"),
	CREATE(2, "创建", "Create"),
	UPDATE(3, "更新", "Update"),
	DELETE(4, "删除", "Delete"),
	STUDY(5, "学习", "Study"),
	EXERCISE(6, "练习", "Exercise"),
	EXAM(7, "考试", "Exam"),
	TRAIN(8, "训练", "Train"),
	TEST(9, "测评", "Test"),
	PARTICIPATE(10, "参赛", "Participate"),
	STOP(11, "停止", "Stop"),
	PAUSE(12, "暂停", "Suspend"),
	START(13, "启动", "Start"),
	UNPAUSE(14, "恢复", "Resume"),
	DOWNLOAD(15, "下载", "Download");

	@Getter
	private final Integer code;

	@Getter
	private final String chinese;

	@Getter
	private final String english;

	OperationType(Integer code, String chinese, String english) {
		this.code = code;
		this.chinese = chinese;
		this.english = english;
	}
}
