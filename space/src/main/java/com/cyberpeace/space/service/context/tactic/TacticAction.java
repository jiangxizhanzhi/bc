package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 战法行动
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `TacticAction` 的 Java 类，用于表示战法行动的相关信息。以下是对其的简要分析： 
 * 1. **类的功能**： 
 * - `TacticAction` 是一个简单的数据传输对象（DTO），主要用于存储与战法行动相关的信息。 
 * - 它包含三个主要属性：`index`、`name` 和 `content`。 
 * 2. **属性说明**： 
 * - `index`：表示步骤的序号，类型为 `Integer`，注解 `@ApiModelProperty` 表示其在 API 文档中的描述为“步骤序号”。 
 * - `name`：表示步骤的名称，类型为 `String`，注解 `@ApiModelProperty` 表示其描述为“步骤名称”。 
 * - `content`：表示行动的描述，类型为 `String`，注解 `@ApiModelProperty` 表示其描述为“行动描述”。 
 * 3. **注解使用**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法，简化了代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，用于生成 API 文档时描述字段的用途。 
 * 4. **设计目的**： 
 * - 该类的设计目的是为了封装战法行动的基本信息，便于在系统中传递和展示。 
 * 总结来说，`TacticAction` 是一个简洁且功能明确的数据类，适用于需要描述战法行动基本信息的场景。 
**/
@Getter
@Setter
public class TacticAction {

	/**
	 * 编号
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "步骤序号")
	private Integer index;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "步骤名称")
	private String name;

	/**
	 * 内容
	 *
	 * @see String
	 */// @ApiModelProperty(value = "行动类型， 常量type=46")
	// private Integer type;
	@ApiModelProperty(value = "行动描述")
	private String content;
}

