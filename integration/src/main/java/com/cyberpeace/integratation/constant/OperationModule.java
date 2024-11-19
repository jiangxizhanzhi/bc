package com.cyberpeace.integratation.constant;

import com.cyberpeace.constant.BaseI18n;
import lombok.Getter;

/**
 * 实操模块
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Enum
 * @see BaseI18n
 * @since 1.0.0
 */
public enum OperationModule implements BaseI18n {
	/**
	 * 操作日志模块
	 *
	 * @see OperationModule
	 */

	SYSTEM(0, "系统", "system"),
	/**
	 * 工具
	 *
	 * @see OperationModule
	 */
	TOOL(1, "工具", "tool"),
	/**
	 * 课程
	 *
	 * @see OperationModule
	 */
	COURSE(2, "课程", "course"),
	/**
	 * 资源
	 *
	 * @see OperationModule
	 */
	RESOURCE(3, "资源", "resource"),
	/**
	 * 帐户
	 *
	 * @see OperationModule
	 */
	ACCOUNT(4, "账号", "account"),
	/**
	 * 场景
	 *
	 * @see OperationModule
	 */
	SCENE(5, "场景", "scene"),
	/**
	 * RTKS
	 *
	 * @see OperationModule
	 */
	RTKS(6, "职业体系", "rtks"),
	/**
	 * 考试
	 *
	 * @see OperationModule
	 */
	EXAM(7, "考试", "exam"),
	/**
	 * 锻炼
	 *
	 * @see OperationModule
	 */
	EXERCISE(8, "习题集", "exercise"),
	/**
	 * 捕获flag
	 *
	 * @see OperationModule
	 */
	CAPTURE_FLAG(9, "夺旗解题", "practice"),
	/**
	 * 理论
	 *
	 * @see OperationModule
	 */
	THEORY(10, "理论基础", "theory"),
	/**
	 * 试卷
	 *
	 * @see OperationModule
	 */
	PAPER(11, "试卷", "exam paper"),
	/**
	 * 任务
	 *
	 * @see OperationModule
	 */
	TASK(12, "任务", "task"),
	/**
	 * 流量
	 *
	 * @see OperationModule
	 */
	FLOW(13, "流量", "flow"),
	/**
	 * 探针
	 *
	 * @see OperationModule
	 */
	PROBE(14, "探针", "probe"),
	/**
	 * 装置
	 *
	 * @see OperationModule
	 */
	DEVICE(15, "靶标", "target"),
	/**
	 * 红色 vs 蓝色
	 *
	 * @see OperationModule
	 */
	RED_VS_BLUE(16, "红蓝对抗", "cyber wargame"),
	/**
	 * 单训练
	 *
	 * @see OperationModule
	 */
	SINGLE_TRAIN(17, "单兵训练", "individual train"),
	/**
	 * 团队训练
	 *
	 * @see OperationModule
	 */
	TEAM_TRAIN(18, "团队训练", "team train");

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

	OperationModule(Integer code, String chinese, String english) {
		this.code = code;
		this.chinese = chinese;
		this.english = english;
	}
}
