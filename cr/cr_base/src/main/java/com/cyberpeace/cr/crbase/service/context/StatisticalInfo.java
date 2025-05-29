package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 统计信息
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `StatisticalInfo` 的 Java 类，位于包 `com.cyberpeace.cr.crbase.service.context` 中。以下是简要分析： 
 * 1. **类的功能**： 
 * - 这是一个用于表示统计信息的简单数据传输对象（DTO）。 
 * - 它包含两个字段：`statistical_state` 和 `value`，分别表示统计状态和对应的值。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段添加了描述性注释，便于 API 文档生成。 
 * 3. **字段说明**： 
 * - `statistical_state`：表示统计的状态，类型为 `Integer`。 
 * - `value`：表示与统计状态相关的具体值，类型为 `Integer`。 
 * 4. **设计目的**： 
 * - 该类主要用于在服务层或接口中传递统计信息，可能用于前端展示或其他逻辑处理。 
 * 总结：这是一个简单的 DTO 类，用于封装统计信息，便于在系统中传递和使用。 
**/
@Getter
@Setter
public class StatisticalInfo {

	/**
	 * 统计状态
	 */
	@ApiModelProperty(value = "统计状态")
	private Integer statistical_state;

	/**
	 * 值
	 */
	@ApiModelProperty(value = "值")
	private Integer value;
}

