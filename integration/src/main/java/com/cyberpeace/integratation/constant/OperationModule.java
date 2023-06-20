package com.cyberpeace.integratation.constant;

import com.cyberpeace.constant.BaseI18n;
import lombok.Getter;

public enum OperationModule implements BaseI18n {
	/**
	 * 操作日志模块
	 */

	SYSTEM(0, "系统", "system"),
	TOOL(1, "工具", "tool"),
	COURSE(2, "课程", "course"),
	RESOURCE(3, "资源", "resource"),
	ACCOUNT(4, "账号", "account"),
	SCENE(5, "场景", "scene"),
	RTKS(6, "职业体系", "rtks"),
	EXAM(7, "考试", "exam"),
	EXERCISE(8, "习题集管理", "exercise"),
	CAPTURE_FLAG(9, "夺旗解题", "practice"),
	THEORY(10, "理论基础", "theory"),
	PAPER(11, "试卷", "exam paper"),
	TASK(12, "任务", "task"),
	FLOW(13, "流量", "flow"),
	PROBE(14, "探针", "probe"),
	DEVICE(15, "靶标", "target"),
	RED_VS_BLUE(16, "红蓝对抗", "cyber wargame"),
	SINGLE_TRAIN(17, "单兵训练", "individual train"),
	TEAM_TRAIN(18, "团队训练", "team train"),
	TRAIN_MANAGE(19, "课程培训管理", "train manage");


	@Getter
	private final Integer code;

	@Getter
	private final String chinese;

	@Getter
	private final String english;

	OperationModule(Integer code, String chinese, String english) {
		this.code = code;
		this.chinese = chinese;
		this.english = english;
	}
}
