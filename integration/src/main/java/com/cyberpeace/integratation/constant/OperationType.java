package com.cyberpeace.integratation.constant;

import com.cyberpeace.constant.BaseI18n;

import lombok.Getter;

/**
 * 实操类型
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `OperationType` 的枚举类，该类实现了 `BaseI18n` 接口。以下是代码的主要组成部分和功能的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.integratation.constant` 
 * - **导入**： 
 * - `com.cyberpeace.constant.BaseI18n` 
 * - `lombok.Getter` 
 * ### 枚举定义 
 * - **枚举名**：`OperationType` 
 * - **接口实现**：`OperationType` 实现了 `BaseI18n` 接口。 
 * ### 枚举常量 
 * 枚举类中定义了一系列的操作类型常量，每个常量包含三个属性： 
 * - **code**：操作类型的唯一标识码（整数） 
 * - **chinese**：操作类型的中文描述 
 * - **english**：操作类型的英文描述 
 * 具体常量包括： 
 * - `LOGIN` (0, "登入", "Log in") 
 * - `SIGN_OUT` (1, "登出", "Sign out") 
 * - `CREATE` (2, "创建", "Create") 
 * - `UPDATE` (3, "更新", "Update") 
 * - `DELETE` (4, "删除", "Delete") 
 * - `STUDY` (5, "学习", "Study") 
 * - `EXERCISE` (6, "练习", "Exercise") 
 * - `EXAM` (7, "考试", "Exam") 
 * - `TRAIN` (8, "训练", "Train") 
 * - `TEST` (9, "测评", "Test") 
 * - `PARTICIPATE` (10, "参赛", "Participate") 
 * - `STOP` (11, "停止", "Stop") 
 * - `PAUSE` (12, "暂停", "Suspend") 
 * - `START` (13, "启动", "Start") 
 * - `UNPAUSE` (14, "恢复", "Resume") 
 * ### 字段和构造器 
 * - **字段**： 
 * - `code`：操作类型的唯一标识码。 
 * - `chinese`：操作类型的中文描述。 
 * - `english`：操作类型的英文描述。 
 * - **构造器**： 
 * - `OperationType(Integer code, String chinese, String english)`：初始化枚举实例的三个属性。 
 * ### Getter 方法 
 * - 使用 Lombok 的 `@Getter` 注解为 `code`, `chinese`, 和 `english` 字段生成了 getter 方法。 
 * ### 总结 
 * 这个枚举类 `OperationType` 定义了一系列操作类型，并为每种类型提供了代码、中文描述和英文描述。这种设计有助于在应用程序中使用统一的操作类型标识，并支持国际化（I18n）。 
**/
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

