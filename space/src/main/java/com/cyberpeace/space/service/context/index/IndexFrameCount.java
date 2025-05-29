package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编号框架计数
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `IndexFrameCount` 的类，用于表示编号框架中的计数逻辑。以下是简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于存储和计算与编号框架相关的计数信息。 
 * - 它包含三个字段：`process`（攻击流程的防御框架）、`target`（攻击目标的防御框架）和 `sum`（两者的总和）。 
 * 2. **注解和属性**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法，简化了代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段提供了描述性信息，便于生成 API 文档。 
 * 3. **方法**： 
 * - `calSum()` 方法用于计算并更新 `sum` 字段，其值为 `process` 和 `target` 的总和。 
 * 4. **设计意图**： 
 * - 类的设计目的是封装与编号框架计数相关的逻辑，提供清晰的接口来管理相关数据。 
 * 总体而言，这是一个简单的数据模型类，结合了 Lombok 和 Swagger 注解，提高了开发效率和代码可读性。 
**/
@Getter
@Setter
public class IndexFrameCount {

	/**
	 * 过程
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "攻击流程的JW防御行动框架")
	private Integer process;

	/**
	 * 目标
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "攻击目标的JW防御行动框架")
	private Integer target;

	/**
	 * @see Integer
	 */
	private Integer sum;

	/**
	 * Cal Sum
	 *
	 * @throws
	 */
	public void calSum() {
		this.sum = this.process + this.target;
	}
}

