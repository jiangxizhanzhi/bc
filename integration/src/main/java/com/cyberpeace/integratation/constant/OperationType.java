package com.cyberpeace.integratation.constant;

import com.cyberpeace.constant.BaseI18n;

import lombok.Getter;

/**
 * 实操类型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Enum
 * @see BaseI18n
 * @since 1.0.0
 */
public enum OperationType implements BaseI18n {
	/**
	 * 操作类型
	 *
	 * @see OperationType
	 */

	LOGIN(0, "登入", "Log in"),
	/**
	 * 登出
	 *
	 * @see OperationType
	 */
	SIGN_OUT(1, "登出", "Sign out"),
	/**
	 * 创建
	 *
	 * @see OperationType
	 */
	CREATE(2, "创建", "Create"),
	/**
	 * 更新
	 *
	 * @see OperationType
	 */
	UPDATE(3, "更新", "Update"),
	/**
	 * 删除
	 *
	 * @see OperationType
	 */
	DELETE(4, "删除", "Delete"),
	/**
	 * 研究
	 *
	 * @see OperationType
	 */
	STUDY(5, "学习", "Study"),
	/**
	 * 练习
	 *
	 * @see OperationType
	 */
	EXERCISE(6, "练习", "Exercise"),
	/**
	 * 考试
	 *
	 * @see OperationType
	 */
	EXAM(7, "考试", "Exam"),
	/**
	 * 训练
	 *
	 * @see OperationType
	 */
	TRAIN(8, "训练", "Train"),
	/**
	 * 测评
	 *
	 * @see OperationType
	 */
	TEST(9, "测评", "Test"),
	/**
	 * 参赛
	 *
	 * @see OperationType
	 */
	PARTICIPATE(10, "参赛", "Participate"),
	/**
	 * 停止
	 *
	 * @see OperationType
	 */
	STOP(11, "停止", "Stop"),
	/**
	 * 暂停
	 *
	 * @see OperationType
	 */
	PAUSE(12, "暂停", "Suspend"),
	/**
	 * 启动
	 *
	 * @see OperationType
	 */
	START(13, "启动", "Start"),
	/**
	 * 恢复
	 *
	 * @see OperationType
	 */
	UNPAUSE(14, "恢复", "Resume");

	/**
	 * 代号
	 *
	 * @see Integer
	 */
	@Getter
	private final Integer code;

	/**
	 * 中文
	 *
	 * @see String
	 */
	@Getter
	private final String chinese;

	/**
	 * 英语
	 *
	 * @see String
	 */
	@Getter
	private final String english;

	OperationType(Integer code, String chinese, String english) {
		this.code = code;
		this.chinese = chinese;
		this.english = english;
	}
}
