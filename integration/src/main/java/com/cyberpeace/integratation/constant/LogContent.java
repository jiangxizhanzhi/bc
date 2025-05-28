/**
 * 这段代码定义了一个名为 `LogContent` 的枚举类，该类实现了 `BaseI18n` 接口，并且使用了 Lombok 库来自动生成 getter 方法。以下是详细的分析和总结： 
 * ### 类定义与继承 
 * - **类名**：`LogContent` 
 * - **实现接口**：`BaseI18n` 
 * - **注解**： 
 * - `@Getter`：由 Lombok 自动生成 `english` 和 `chinese` 属性的 getter 方法。 
 * ### 枚举常量 
 * 枚举类中定义了一系列常量，每个常量都有英文和中文两种语言的描述： 
 * - `CR_FORMAT`：操作日志内容描述前缀。 
 * - `SCENE_FORMAT`：场景格式。 
 * - `COURSE_FORMAT`：课程形式。 
 * - `PRACTICE_FORMAT`：练习形式。 
 * - `THEORY_FORMAT`：理论格式。 
 * - `EXERCISE`：锻炼。 
 * - `THEORY`：理论。 
 * - `PRACTICE_SET`：实践设置。 
 * - `SINGLE_TRAIN_TEMPLATE`：单训练模板。 
 * - `TEAM_TRAIN_TEMPLATE`：团队训练模板。 
 * - `RB_ROOM_TEMPLATE`：RB 房间模板。 
 * - `TEAM_TRAIN_ROOM`：团队训练房间。 
 * - `RB_TRAIN_ROOM`：RB训练房间。 
 * - `RANGE_TEST_ROOM`：测距试验室。 
 * - `RTKS_FRAME`：RTKS框架。 
 * - `LEVEL`：水平。 
 * - `FRAME`：框架。 
 * - `KNOWLEDGE_CATEGORY`：知识类别。 
 * - `KNOWLEDGE`：知识。 
 * - `ROLE`：岗位。 
 * - `SKILL`：技能。 
 * - `TASK`：任务。 
 * - `CORRECT`：正确。 
 * - `INCORRECT`：不正确。 
 * - `BATCH_DELETE_FORMAT`：批量删除格式。 
 * ### 构造函数 
 * - `LogContent(String english, String chinese)`：构造函数用于初始化枚举常量的英文和中文描述。 
 * ### 字段 
 * - `private final String english;`：英文描述。 
 * - `private final String chinese;`：中文描述。 
 * ### 总结 
 * - **用途**：这个枚举类用于存储和管理不同类型的日志内容及其对应的国际化描述（英文和中文）。 
 * - **特点**：使用了 Lombok 注解来简化代码，减少样板代码。 
 * - **结构**：枚举常量包含了各种日志内容的描述，方便在应用中进行多语言支持。 
 * 通过这种方式，可以确保在应用程序的不同部分使用统一的日志内容描述，并且易于扩展和维护。 
**/
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

