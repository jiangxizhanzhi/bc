package com.cyberpeace.integratation.constant;

import com.cyberpeace.constant.BaseI18n;

import lombok.Getter;

public enum LogContent implements BaseI18n {
	/**
	 * 操作日志内容描述前缀
	 */

	CR_FORMAT("%s %s \"%s\"", "%s%s \"%s\""),
	SCENE_FORMAT("Scene temple = %s","场景模板 = %s"),
	COURSE_FORMAT("Name of course: %s, Name of lesson: %s.", "课程名：%s，课时名：%s。"),
	PRACTICE_FORMAT("Exercise: \"%s\", the user answered %s.", "实操题：\"%s\"，用户回答%s。"),
	THEORY_FORMAT("Theory: \"%s\", the user answered %s.", "理论题：\"%s\"，用户回答%s。"),
	EXERCISE("Exercise", "实操题"),
	THEORY("Theory", "理论题"),
	PRACTICE_SET("Practice set","习题集"),
	SINGLE_TRAIN_TEMPLATE("Single train template","单兵训练模板"),
	TEAM_TRAIN_TEMPLATE("Team train template","团队训练模板"),
	RB_ROOM_TEMPLATE("Cyber Wargame template","红蓝对抗模板"),

	TEAM_TRAIN_ROOM("Team Train scene","团队训练场景"),
	RB_TRAIN_ROOM("Cyber Wargame scene","红蓝对抗场景"),
	RANGE_TEST_ROOM("Range Test scene","靶场测评场景"),

	RTKS_FRAME("%s %s", "%s%s"),
	LEVEL("Level", "岗位层级"),
	FRAME("Network Security Frame", "网络安全框架"),
	KNOWLEDGE_CATEGORY("Knowledge Category", "知识类别"),
	KNOWLEDGE("Knowledge", "知识"),
	ROLE("Role", "岗位"), SKILL("Skill", "技能"),
	TASK("Task", "岗位职责"),
	CORRECT("correctly", "正确"),
	INCORRECT("incorrectly", "错误"),
	BATCH_DELETE_FORMAT("Batch delete %s", "批量删除 %s");

	@Getter
	private final String english;

	@Getter
	private final String chinese;

	LogContent(String english, String chinese) {
		this.english = english;
		this.chinese = chinese;
	}
}

