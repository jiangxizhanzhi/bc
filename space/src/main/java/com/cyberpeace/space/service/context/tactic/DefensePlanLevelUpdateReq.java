/**
 * 这段代码定义了一个名为 `DefensePlanLevelUpdateReq` 的 Java 类，用于表示防御计划层级更新的请求体。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * 该类主要用于封装防御计划层级更新所需的请求参数。 
 * 2. **注解与框架**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为接口文档生成提供字段描述。 
 * 3. **属性说明**： 
 * - `level`: 表示防御计划的层级，类型为 `SpaceFrameLevel`，并标注了 Swagger 文档的描述为“框架层级”。 
 * - `resourceId`: 表示资源 ID，类型为 `String`，Swagger 文档描述为“加入的 id”。 
 * 4. **继承与依赖**： 
 * - 该类依赖于 `com.cyberpeace.space.service.context.frame.SpaceFrameLevel` 类。 
 * - 使用了 Lombok 和 Swagger 相关库。 
 * 5. **设计意图**： 
 * 这是一个典型的 DTO（数据传输对象）类，用于在服务层或接口层传递数据。 
 * 总结：这是一个简洁且功能明确的类，用于支持防御计划层级更新的请求参数封装。 
**/
package com.cyberpeace.space.service.context.tactic;

import com.cyberpeace.space.service.context.frame.SpaceFrameLevel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 防御计划层级更新请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class DefensePlanLevelUpdateReq {

	/**
	 * 层级
	 *
	 * @see SpaceFrameLevel
	 */
	@ApiModelProperty(value = "框架层级")
	private SpaceFrameLevel level;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "加入的id")
	private String resourceId;
}
