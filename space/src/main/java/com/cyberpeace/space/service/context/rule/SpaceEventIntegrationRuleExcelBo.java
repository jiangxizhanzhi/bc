package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间事件集成统治胜过Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceEventIntegrationRuleExcelBo` 的 Java 类，属于 `com.cyberpeace.space.service.context.rule` 包。以下是代码的主要分析和总结： 
 * ### 主要功能： 
 * 该类是一个数据传输对象（DTO），用于表示与空间事件集成相关的规则信息。它通过 Lombok 注解简化了 getter 和 setter 方法的生成，并使用 Swagger 注解来描述字段的含义。 
 * ### 关键点分析： 
 * 1. **类的基本结构**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的getter和setter方法。 
 * - 类名表明其用途是处理空间事件集成规则的 Excel 数据对象（BO：Business Object）。 
 * 2. **字段说明**： 
 * - `type`：表示规则的类型，用字符串表示，注释中提到可以通过 `@see String` 进一步了解。 
 * - `description`：提供规则的简要描述，字段值为字符串。 
 * - `content`：存储规则的详细内容，字段值为字符串。 
 * - `tool`：表示实现规则所使用的工具，字段值为字符串。 
 * 3. **Swagger 注解**： 
 * - 使用了 `@ApiModelProperty` 注解为每个字段提供了元数据，便于文档生成或接口展示时描述字段的用途。 
 * 4. **注释**： 
 * - 类的注释说明了该类的功能是用于空间事件集成规则的业务对象。 
 * - 每个字段都有详细的注释，描述了字段的作用和预期数据类型。 
 * ### 总结： 
 * 这是一个简单的数据传输对象类，主要用于封装与空间事件集成规则相关的信息。通过 Lombok 和 Swagger 注解，提高了代码的简洁性和可维护性。 
**/
@Getter
@Setter
public class SpaceEventIntegrationRuleExcelBo {

	/**
	 * 类型
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "检测类型")
	private String type;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "内容简介")
	private String description;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "内容详情")
	private String content;

	/**
	 * 工具
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "应用工具")
	private String tool;
}

