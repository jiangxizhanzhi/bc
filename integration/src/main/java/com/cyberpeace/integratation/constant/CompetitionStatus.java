/**
 * 这段代码定义了一个名为 `CompetitionStatus` 的枚举类，用于表示比赛的状态。以下是简要分析： 
 * 1. **包和导入**： 
 * - 该类位于 `com.cyberpeace.integratation.constant` 包下。 
 * - 导入了 `GenericEnum` 接口和 `lombok.Getter` 注解。 
 * 2. **枚举定义**： 
 * - `CompetitionStatus` 枚举实现了 `GenericEnum` 接口。 
 * - 枚举包含四个常量：`NO_START`, `START`, `PAUSE`, 和 `END`，分别表示比赛的不同状态。 
 * 3. **字段**： 
 * - `code`：表示状态的整数编码，使用 `@Getter` 注解，可以直接访问。 
 * - `desc`：表示状态的描述字符串，同样使用 `@Getter` 注解。 
 * 4. **构造函数**： 
 * - 构造函数接受一个整数 `code` 和一个字符串 `desc`，并初始化这两个字段。 
 * 5. **用途**： 
 * - 这个枚举类可以用来表示比赛的不同状态，并且可以通过 `code` 和 `desc` 获取状态的具体信息。 
 * 总结：这是一个表示比赛状态的枚举类，提供了状态码和描述，并通过 `@Getter` 注解方便地获取这些信息。 
**/
package com.cyberpeace.integratation.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

/**
 * 竞争状态
 *
 * @author SN
 * @date 2024/11/08
 */
public enum CompetitionStatus implements GenericEnum {

	/**
	 * 比赛状态
	 */
	NO_START(0, "未开赛"),
	/**
	 * 开始
	 */
	START(1, "进行中"),
	/**
	 * 暂停
	 */
	PAUSE(2, "暂停"),
	/**
	 * 结束
	 */
	END(3, "结束");

	/**
	 * 代号
	 */
	@Getter
    private Integer code;

	/**
	 * 描述
	 */
	@Getter
	private String desc;

	CompetitionStatus(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
