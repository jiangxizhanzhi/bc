/**
 * 这段代码定义了一个名为 `DynamicData` 的 Java 类，位于包 `com.cyberpeace.cr.crbase.service.context` 下。以下是代码的主要分析： 
 * 1. **类的功能**： 
 * 该类是一个用于存储动态数据的实体类，主要用于描述某个操作的相关信息。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为 API 文档生成提供字段描述。 
 * 3. **主要字段**： 
 * - `actionType`：表示操作类型，类型为 `Integer`，通过 `@ApiModelProperty` 注解标注其描述为“动作类型”。 
 * - `actionTime`：表示操作时间，类型为 `Date`，描述为“动作时间”。 
 * - `missionName`：表示任务名称，类型为 `String`，描述为“任务名”。 
 * 4. **用途**： 
 * 这个类可以作为数据传输对象（DTO）或业务逻辑中的数据模型，用于封装与操作相关的动态数据。 
 * 总结：这是一个简单的 Java Bean 类，用于存储与操作相关的信息，并支持通过注解增强功能和文档生成。 
**/
package com.cyberpeace.cr.crbase.service.context;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 动态数据
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class DynamicData {

	/**
	 * 操作类型
	 */
	@ApiModelProperty(value = "动作类型")
	private Integer actionType;

	/**
	 * 操作时间
	 */
	@ApiModelProperty(value = "动作时间")
	private Date actionTime;

	/**
	 * 任务名称
	 */
	@ApiModelProperty(value = "任务名")
	private String missionName;
}
