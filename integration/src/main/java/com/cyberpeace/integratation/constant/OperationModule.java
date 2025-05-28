/**
 * 这段代码定义了一个名为 `OperationModule` 的枚举类，该类实现了 `BaseI18n` 接口。这个枚举类用于表示操作模块的不同类型，并且每个枚举实例都有一个对应的代码、中文名称和英文名称。 
 * ### 主要组成部分： 
 * 1. **枚举常量**： 
 * - `SYSTEM`, `TOOL`, `COURSE`, `RESOURCE`, `ACCOUNT`, `SCENE`, `RTKS`, `EXAM`, `EXERCISE`, `CAPTURE_FLAG`, `THEORY`, `PAPER`, `TASK`, `FLOW`, `PROBE`, `DEVICE`, `RED_VS_BLUE`, `SINGLE_TRAIN`, `TEAM_TRAIN`。 
 * - 每个枚举常量都有一个整数代码（例如，`SYSTEM` 的代码是 `0`），中文名称（例如，`SYSTEM` 的中文名称是 `"系统"`）和英文名称（例如，`SYSTEM` 的英文名称是 `"system"`）。 
 * 2. **属性**： 
 * - `code`: 整数类型的代码，使用 `@Getter` 注解表示可以通过 `getCode()` 方法获取。 
 * - `chinese`: 字符串类型的中文名称，使用 `@Getter` 注解表示可以通过 `getChinese()` 方法获取。 
 * - `english`: 字符串类型的英文名称，使用 `@Getter` 注解表示可以通过 `getEnglish()` 方法获取。 
 * 3. **构造函数**： 
 * - 枚举类有一个私有的构造函数，接受三个参数：`Integer code`, `String chinese`, `String english`，用于初始化枚举实例的属性。 
 * 4. **实现接口**： 
 * - 枚举类实现了 `BaseI18n` 接口，这意味着它可能需要提供一些与国际化相关的功能。 
 * ### 总结 
 * - `OperationModule` 枚举类定义了操作模块的不同类型，并为每个类型提供了代码、中文名称和英文名称。 
 * - 这些信息可以用于在应用程序中以不同的语言显示模块名称，或者根据模块代码来识别特定的操作模块。 
 * - 使用 Lombok 的 `@Getter` 注解简化了属性的访问器方法的生成。 
 * 这个枚举类的设计使得操作模块的管理和国际化变得更加方便和统一。 
**/
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
