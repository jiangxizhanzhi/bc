package com.cyberpeace.integratation.constant;

import com.cyberpeace.constant.BaseI18n;

import lombok.Getter;

/**
 * 日志内容
 *
 * @author SN
 * @date 2024/11/08
 */
public enum LogContent implements BaseI18n {
	/**
	 * 操作日志内容描述前缀
	 */

	CR_FORMAT("%s %s \"%s\"", "%s%s \"%s\""),
	/**
	 * 场景格式
	 */
	SCENE_FORMAT("Scene temple = %s","场景模板 = %s"),
	/**
	 * 课程形式
	 */
	COURSE_FORMAT("Name of course: %s, Name of lesson: %s.", "课程名：%s，课时名：%s。"),
	/**
	 * 练习形式
	 */
	PRACTICE_FORMAT("Exercise: \"%s\", the user answered %s.", "实操题：\"%s\"，用户回答%s。"),
	/**
	 * 理论格式
	 */
	THEORY_FORMAT("Theory: \"%s\", the user answered %s.", "理论题：\"%s\"，用户回答%s。"),
	/**
	 * 锻炼
	 */
	EXERCISE("Exercise", "实操题"),
	/**
	 * 理论
	 */
	THEORY("Theory", "理论题"),
	/**
	 * 实践设置
	 */
	PRACTICE_SET("Practice set","习题集"),
	/**
	 * 单训练模板
	 */
	SINGLE_TRAIN_TEMPLATE("Single train template","单兵训练模板"),
	/**
	 * 团队训练模板
	 */
	TEAM_TRAIN_TEMPLATE("Team train template","团队训练模板"),
	/**
	 * RB 房间模板
	 */
	RB_ROOM_TEMPLATE("Cyber Wargame template","红蓝对抗模板"),

	/**
	 * 团队训练房间
	 */
	TEAM_TRAIN_ROOM("Team Train scene","团队训练场景"),
	/**
	 * RB训练房间
	 */
	RB_TRAIN_ROOM("Cyber Wargame scene","红蓝对抗场景"),
	/**
	 * 测距试验室
	 */
	RANGE_TEST_ROOM("Range Test scene","靶场测评场景"),

	/**
	 * RTKS框架
	 */
	RTKS_FRAME("%s %s", "%s%s"),
	/**
	 * 水平
	 */
	LEVEL("Level", "岗位层级"),
	/**
	 * 框架
	 */
	FRAME("Network Security Frame", "网络安全框架"),
	/**
	 * 知识类别
	 */
	KNOWLEDGE_CATEGORY("Knowledge Category", "知识类别"),
	/**
	 * 知识
	 */
	KNOWLEDGE("Knowledge", "知识"),
	/**
	 * 岗位
	 */
	ROLE("Role", "岗位"),
	/**
	 * 技能
	 */
	SKILL("Skill", "技能"),
	/**
	 * 任务
	 */
	TASK("Task", "岗位职责"),
	/**
	 * 正确
	 */
	CORRECT("correctly", "正确"),
	/**
	 * 不對
	 */
	INCORRECT("incorrectly", "错误"),
	/**
	 * 批量删除格式
	 */
	BATCH_DELETE_FORMAT("Batch delete %s", "批量删除 %s");

	/**
	 * 英语
	 */
	@Getter
	private final String english;

	/**
	 * 中文
	 */
	@Getter
	private final String chinese;

	LogContent(String english, String chinese) {
		this.english = english;
		this.chinese = chinese;
	}
}

