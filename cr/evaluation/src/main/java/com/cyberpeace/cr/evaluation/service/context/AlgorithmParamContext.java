/**
 * 这段代码定义了一个名为 `AlgorithmParamContext` 的 Java 类，该类用于表示算法参数的上下文。以下是详细的分析： 
 * ### 类定义 
 * - **包名**: `com.cyberpeace.cr.evaluation.service.context` 
 * - **类名**: `AlgorithmParamContext` 
 * - **注解**: 
 * - `@Getter`: 自动生成所有属性的 getter 方法。 
 * - `@Setter`: 自动生成所有属性的 setter 方法。 
 * ### 属性 
 * 1. **paramName** 
 * - **类型**: `String` 
 * - **注解**: 
 * - `@ApiModelProperty(value = "参数名称", required = true)`: Swagger 注解，用于 API 文档生成。 
 * - `@NotNull`: 验证注解，确保该字段不能为空。 
 * - **用途**: 表示参数的名称。 
 * 2. **desc** 
 * - **类型**: `String` 
 * - **注解**: 
 * - `@ApiModelProperty(value = "描述", required = false)`: Swagger 注解，用于 API 文档生成。 
 * - **用途**: 表示参数的描述信息。 
 * 3. **paramType** 
 * - **类型**: `String` 
 * - **注解**: 
 * - `@ApiModelProperty(value = "参数类型", required = true)`: Swagger 注解，用于 API 文档生成。 
 * - `@NotNull`: 验证注解，确保该字段不能为空。 
 * - **用途**: 表示参数的类型。 
 * 4. **defaultValue** 
 * - **类型**: `String` 
 * - **注解**: 
 * - `@ApiModelProperty(value = "默认值", required = true)`: Swagger 注解，用于 API 文档生成。 
 * - `@NotNull`: 验证注解，确保该字段不能为空。 
 * - **用途**: 表示参数的默认值。 
 * ### 总结 
 * - **功能**: 该类用于封装算法参数的相关信息，包括参数名称、描述、类型和默认值。 
 * - **验证**: 使用了 `@NotNull` 注解来确保某些字段（如 `paramName`, `paramType`, `defaultValue`）在使用时必须提供有效值。 
 * - **文档**: 使用了 Swagger 注解 (`@ApiModelProperty`) 来帮助生成 API 文档，便于前端和其他服务理解该类的用途。 
 * 这个类是一个典型的 Java Bean，适用于在服务层或控制器层中传递算法参数的信息。 
**/
package com.cyberpeace.cr.evaluation.service.context;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * algorithm param 上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class AlgorithmParamContext {

	/**
	 * 参数名称
	 */
	@ApiModelProperty(value = "参数名称", required = true)
	@NotNull
	private String paramName;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述", required = false)
	private String desc;

	/**
	 * param 类型
	 */
	@ApiModelProperty(value = "参数类型", required = true)
	@NotNull
	private String paramType;

	/**
	 * 违约值
	 */
	@ApiModelProperty(value = "默认值", required = true)
	@NotNull
	private String defaultValue;
}
