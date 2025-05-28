/**
 * 这段代码定义了一个名为 `MissionStageScheme` 的 Java 类，位于包 `com.cyberpeace.cr.crbase.service.context` 中。以下是对其的简要分析： 
 * 1. **类的功能**： 
 * - 该类用于描述任务阶段的编排，包含阶段、任务列表和动态列表。 
 * 2. **属性说明**： 
 * - `stage`：表示任务的阶段，类型为字符串。 
 * - `missions`：存储该阶段的任务列表，类型为字符串列表。 
 * - `dynamic`：存储与该阶段相关的动态信息，类型为字符串列表。 
 * 3. **注解功能**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法，简化了代码编写。 
 * 4. **设计意图**： 
 * - 通过该类可以灵活地配置和管理不同阶段的任务及其相关动态信息，适用于需要阶段化任务处理的场景。 
 * 总结：这是一个简单的数据模型类，主要用于任务阶段的结构化表示和管理。 
**/
package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 任务阶段编排
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MissionStageScheme {

	/**
	 * 阶段
	 */
	private String stage;

	/**
	 * 任务
	 */
	private List<String> missions;

	/**
	 * 动态
	 */
	private List<String> dynamic;
}
