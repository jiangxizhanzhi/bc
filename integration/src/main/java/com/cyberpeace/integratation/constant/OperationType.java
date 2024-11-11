package com.cyberpeace.integratation.constant;

import com.cyberpeace.constant.BaseI18n;

import lombok.Getter;

/**
 * 实操类型
 *
 * @author SN
 * @date 2024/11/11
 */
public enum OperationType implements BaseI18n {
	/**
	 * 操作类型
	 */

	LOGIN(0, "登入", "Log in"),
	/**
	 * 登出
	 */
	SIGN_OUT(1, "登出", "Sign out"),
	/**
	 * 创造
	 */
	CREATE(2, "创建", "Create"),
	/**
	 * 更新
	 */
	UPDATE(3, "更新", "Update"),
	/**
	 * 删除
	 */
	DELETE(4, "删除", "Delete"),
	/**
	 * 研究
	 */
	STUDY(5, "学习", "Study"),
	/**
	 * 锻炼
	 */
	EXERCISE(6, "练习", "Exercise"),
	/**
	 * 考试
	 */
	EXAM(7, "考试", "Exam"),
	/**
	 * 训练
	 */
	TRAIN(8, "训练", "Train"),
	/**
	 * 测试
	 */
	TEST(9, "测评", "Test"),
	/**
	 * 参与
	 */
	PARTICIPATE(10, "参赛", "Participate"),
	/**
	 * 停
	 */
	STOP(11, "停止", "Stop"),
	/**
	 * 暂停
	 */
	PAUSE(12, "暂停", "Suspend"),
	/**
	 * 开始
	 */
	START(13, "启动", "Start"),
	/**
	 * 取消暂停
	 */
	UNPAUSE(14, "恢复", "Resume");

	/**
	 * 代号
	 */
	@Getter
	private final Integer code;

	/**
	 * 中文
	 */
	@Getter
	private final String chinese;

	/**
	 * 英语
	 */
	@Getter
	private final String english;

	OperationType(Integer code, String chinese, String english) {
		this.code = code;
		this.chinese = chinese;
		this.english = english;
	}
}
