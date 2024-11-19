package com.cyberpeace.integratation.constant;

import com.cyberpeace.constant.BaseI18n;

import lombok.Getter;

/**
 * 日志内容
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Enum
 * @see BaseI18n
 * @since 1.0.0
 */
public enum LogContent implements BaseI18n {
	/**
	 * 操作日志内容描述前缀
	 *
	 * @see LogContent
	 */

	CR_FORMAT("%s %s \"%s\"", "%s%s \"%s\""),
	/**
	 * 场景格式
	 *
	 * @see LogContent
	 */
	SCENE_FORMAT("Scene temple = %s","场景模板 = %s"),
	/**
	 * 课程格式
	 *
	 * @see LogContent
	 */
	COURSE_FORMAT("Name of course: %s, Name of lesson: %s.", "课程名：%s，课时名：%s。"),
	/**
	 * 练习格式
	 *
	 * @see LogContent
	 */
	PRACTICE_FORMAT("Exercise: \"%s\", the user answered %s.", "实操题：\"%s\"，用户回答%s。"),
	/**
	 * 理论格式
	 *
	 * @see LogContent
	 */
	THEORY_FORMAT("Theory: \"%s\", the user answered %s.", "理论题：\"%s\"，用户回答%s。"),
	/**
	 * 锻炼
	 *
	 * @see LogContent
	 */
	EXERCISE("Exercise", "实操题"),
	/**
	 * 理论
	 *
	 * @see LogContent
	 */
	THEORY("Theory", "理论题"),
	/**
	 * 练习设置
	 *
	 * @see LogContent
	 */
	PRACTICE_SET("Practice set","习题集"),
	/**
	 * 单训练模板
	 *
	 * @see LogContent
	 */
	SINGLE_TRAIN_TEMPLATE("Single train template","单兵训练模板"),
	/**
	 * 团队训练模板
	 *
	 * @see LogContent
	 */
	TEAM_TRAIN_TEMPLATE("Team train template","团队训练模板"),
	/**
	 * 红蓝对抗房间模板
	 *
	 * @see LogContent
	 */
	RB_ROOM_TEMPLATE("Cyber Wargame template","红蓝对抗模板"),

	/**
	 * 团队训练房间
	 *
	 * @see LogContent
	 */
	TEAM_TRAIN_ROOM("Team Train scene","团队训练场景"),
	/**
	 * 红蓝对抗训练房间
	 *
	 * @see LogContent
	 */
	RB_TRAIN_ROOM("Cyber Wargame scene","红蓝对抗场景"),
	/**
	 * 测距试验室
	 *
	 * @see LogContent
	 */
	RANGE_TEST_ROOM("Range Test scene","靶场测评场景"),

	/**
	 * RTKS框架
	 *
	 * @see LogContent
	 */
	RTKS_FRAME("%s %s", "%s%s"),
	/**
	 * 水平
	 *
	 * @see LogContent
	 */
	LEVEL("Level", "岗位层级"),
	/**
	 * 框架
	 *
	 * @see LogContent
	 */
	FRAME("Network Security Frame", "网络安全框架"),
	/**
	 * 知识点类别
	 *
	 * @see LogContent
	 */
	KNOWLEDGE_CATEGORY("Knowledge Category", "知识类别"),
	/**
	 * 知识点
	 *
	 * @see LogContent
	 */
	KNOWLEDGE("Knowledge", "知识"),
	/**
	 * 岗位
	 *
	 * @see LogContent
	 */
	ROLE("Role", "岗位"),
	/**
	 * 技能
	 *
	 * @see LogContent
	 */
	SKILL("Skill", "技能"),
	/**
	 * 任务
	 *
	 * @see LogContent
	 */
	TASK("Task", "岗位职责"),
	/**
	 * 正确
	 *
	 * @see LogContent
	 */
	CORRECT("correctly", "正确"),
	/**
	 * 不對
	 *
	 * @see LogContent
	 */
	INCORRECT("incorrectly", "错误"),
	/**
	 * 批量删除格式
	 *
	 * @see LogContent
	 */
	BATCH_DELETE_FORMAT("Batch delete %s", "批量删除 %s");

	/**
	 * 英语
	 *
	 * @see String
	 */
	@Getter
	private final String english;

	/**
	 * 中文
	 *
	 * @see String
	 */
	@Getter
	private final String chinese;

	LogContent(String english, String chinese) {
		this.english = english;
		this.chinese = chinese;
	}
}

