/**
 * 这段代码定义了一个名为 `MpRiskCount` 的 Java 类，该类用于表示中台系统的风险计数信息。以下是对此代码的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.integratation.model.mp` 
 * - 该类位于 `com.cyberpeace.integratation.model.mp` 包下。 
 * - **导入语句** 
 * - 导入了 `Serializable` 接口，表示该类可以序列化。 
 * - 导入了 Swagger 注解 `@ApiModelProperty`，用于生成 API 文档。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * ### 类注释 
 * - 类名为 `MpRiskCount`，注释说明这是一个“中台风险计数”类，并且作者是 SN，日期是 2024 年 11 月 8 日。 
 * ### 类属性 
 * - **highCount**：表示高风险的数量，类型为 `Integer`。 
 * - **mediumCount**：表示中风险的数量，类型为 `Integer`。 
 * - **lowCount**：表示低风险的数量，类型为 `Integer`。 
 * ### 属性注释 
 * - 每个属性都有对应的 Swagger 注解 `@ApiModelProperty`，用于在生成的 API 文档中描述这些字段的含义。 
 * ### 总结 
 * - `MpRiskCount` 类是一个简单的数据传输对象（DTO），用于存储和传递中台系统中的风险计数信息。 
 * - 通过 Lombok 的注解减少了样板代码，使得代码更简洁。 
 * - 通过 Swagger 注解，可以方便地生成 API 文档，便于前端开发人员理解如何使用这个类。 
 * 这个类的设计简单明了，适合用于中台系统中处理风险计数的数据传输场景。 
**/
package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 中台风险计数
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MpRiskCount implements Serializable {

	/**
	 * 高风险
	 */
	@ApiModelProperty("高风险")
	private Integer highCount;

	/**
	 * 中风险
	 */
	@ApiModelProperty("中风险")
	private Integer mediumCount;

	/**
	 * 低风险
	 */
	@ApiModelProperty("低风险")
	private Integer lowCount;

}
