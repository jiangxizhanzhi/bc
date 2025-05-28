/**
 * 这段代码定义了一个名为 `TaskCounter` 的 Java 类，用于表示任务统计计数的结果。以下是代码的简要分析： 
 * 1. **类功能**： 
 * - `TaskCounter` 类主要用于统计不同类型任务的数量，包括已完成、已释放和未发布的任务。 
 * 2. **字段**： 
 * - `finished`：表示已完成的任务数量。 
 * - `released`：表示已释放（进行中）的任务数量。 
 * - `notReleased`：表示未发布的任务数量。 
 * 3. **注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法，简化了代码。 
 * 4. **枚举类型**： 
 * - 内部定义了一个 `TaskType` 枚举，表示任务的不同状态： 
 * - `FINISHED`：已完成任务，对应代码 `0` 和描述 `"已完成"`。 
 * - `RELEASED`：进行中任务，对应代码 `1` 和描述 `"进行中"`。 
 * - `NOTRELEASED`：未发布任务，对应代码 `2` 和描述 `"未下发"`。 
 * 5. **构造方法**： 
 * - 枚举 `TaskType` 提供了一个构造方法，用于初始化每个枚举值的 `code` 和 `desc` 属性。 
 * 6. **用途**： 
 * - 这个类可以用于任务管理系统的统计功能，方便记录和查询不同状态任务的数量。 
 * 总结：该类通过简单的字段和枚举类型实现了任务统计的功能，代码结构清晰，易于扩展和维护。 
**/
package com.cyberpeace.cr.crbase.service.context;

import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

/**
 * 任务统计计数结果
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class TaskCounter {

	/**
	 * 完成
	 */
	private Integer finished;

	/**
	 * 释放
	 */
	private Integer released;

	/**
	 * 未发布
	 */
	private Integer notReleased;

	public enum TaskType {

		FINISHED(0, "已完成"), RELEASED(1, "进行中"), NOTRELEASED(2, "未下发");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private TaskType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}
