package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Space 算法参数
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceAlgorithmParameter` 的 Java 类，用于表示与 Space 算法相关的参数对象。以下是对其的简要分析： 
 * ### 功能描述： 
 * - **类的作用**：该类封装了 Space 算法中所需参数的相关信息，包括参数的名称、类型、默认值以及描述。 
 * - **注解使用**：通过 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法，简化代码编写。 
 * ### 属性详解： 
 * 1. **name**: 
 * - 数据类型：`String` 
 * - 注解说明：参数名称，用于标识具体的参数。 
 * 2. **type**: 
 * - 数据类型：`Integer` 
 * - 注解说明：参数类型，可能对应某种枚举或分类。 
 * 3. **defaultValue**: 
 * - 数据类型：`String` 
 * - 注解说明：参数的默认值，便于初始化或提供默认配置。 
 * 4. **description**: 
 * - 数据类型：`String` 
 * - 注解说明：参数的详细描述，帮助理解其用途和意义。 
 * ### 特点总结： 
 * - 使用了 Swagger 注解 (`@ApiModelProperty`) 来描述每个字段的用途，方便生成 API 文档。 
 * - 借助 Lombok 减少了冗长的 getter 和 setter 方法实现。 
 * - 类设计清晰，适合用作数据传输对象（DTO）或业务逻辑中的参数模型。 
**/
@Getter
@Setter
public class SpaceAlgorithmParameter {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "参数名称")
	private String name;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "参数类型")
	private Integer type;

	/**
	 * 默认值
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "默认值")
	private String defaultValue;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "参数说明")
	private String description;
}

