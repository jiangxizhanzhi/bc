package com.cyberpeace.integratation.constant;

import com.cyberpeace.constant.BaseI18n;
import lombok.Getter;

/**
 * 实操模块
 *
 * @author SN
 * @date 2024/11/08
 */
public enum OperationModule implements BaseI18n {
	/**
	 * 操作日志模块
	 */

	SYSTEM(0, "系统", "system"),
	/**
	 * 工具
	 */
	TOOL(1, "工具", "tool"),
	/**
	 * 课程
	 */
	COURSE(2, "课程", "course"),
	/**
	 * 资源
	 */
	RESOURCE(3, "资源", "resource"),
	/**
	 * 帐户
	 */
	ACCOUNT(4, "账号", "account"),
	/**
	 * 场景
	 */
	SCENE(5, "场景", "scene"),
	/**
	 * RTKS
	 */
	RTKS(6, "职业体系", "rtks"),
	/**
	 * 考试
	 */
	EXAM(7, "考试", "exam"),
	/**
	 * 锻炼
	 */
	EXERCISE(8, "习题集", "exercise"),
	/**
	 * 捕获标志
	 */
	CAPTURE_FLAG(9, "夺旗解题", "practice"),
	/**
	 * 理论
	 */
	THEORY(10, "理论基础", "theory"),
	/**
	 * 纸
	 */
	PAPER(11, "试卷", "exam paper"),
	/**
	 * 任务
	 */
	TASK(12, "任务", "task"),
	/**
	 * 流
	 */
	FLOW(13, "流量", "flow"),
	/**
	 * 探针
	 */
	PROBE(14, "探针", "probe"),
	/**
	 * 装置
	 */
	DEVICE(15, "靶标", "target"),
	/**
	 * 红色 vs 蓝色
	 */
	RED_VS_BLUE(16, "红蓝对抗", "cyber wargame"),
	/**
	 * 单训练
	 */
	SINGLE_TRAIN(17, "单兵训练", "individual train"),
	/**
	 * 团队训练
	 */
	TEAM_TRAIN(18, "团队训练", "team train");

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

	OperationModule(Integer code, String chinese, String english) {
		this.code = code;
		this.chinese = chinese;
		this.english = english;
	}
}
