/**
 * 这段代码定义了一个名为 `EvalAlgorithmEditRsp` 的类，该类用于表示评估算法编辑的响应。这个类继承自 `EvalAlgorithmRef` 类，并使用了 Lombok 库的 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法。下面是详细的分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.cr.evaluation.controller.rsp` 
 * - **类名**：`EvalAlgorithmEditRsp` 
 * - **继承关系**：`EvalAlgorithmEditRsp` 继承自 `EvalAlgorithmRef` 
 * ### 属性 
 * 1. **cr** 
 * - **类型**：`boolean` 
 * - **注释**：靶场 
 * - **注解**：`@ApiModelProperty(value = "算法矩阵内容一致性")` 
 * - **说明**：表示算法矩阵内容的一致性。 
 * 2. **msg** 
 * - **类型**：`String` 
 * - **注释**：味精 
 * - **注解**：`@ApiModelProperty(value = "算法矩阵内容一致性返回信息")` 
 * - **说明**：表示算法矩阵内容一致性的返回信息。 
 * ### 注解 
 * - **Lombok 注解** 
 * - `@Getter` 和 `@Setter`：由 Lombok 自动生成属性的 getter 和 setter 方法。 
 * - **Swagger 注解** 
 * - `@ApiModelProperty`：用于描述 API 中使用的模型属性。`value` 字段提供了对属性的简要描述。 
 * ### 总结 
 * - **功能**：`EvalAlgorithmEditRsp` 类用于封装评估算法编辑操作后的响应结果。 
 * - **用途**：通过继承 `EvalAlgorithmRef` 类，该类可以包含与评估算法相关的基础数据。 
 * - **扩展性**：通过添加新的属性和方法，可以轻松扩展该类以满足更多的需求。 
 * - **可读性**：使用 Swagger 注解使得 API 文档能够自动生成，方便其他开发者理解和使用。 
 * ### 作者信息 
 * - **作者**：SN 
 * - **日期**：2024年11月8日 
 * 这段代码简洁明了，通过继承和注解的方式实现了类的功能，并且具备良好的可扩展性和文档生成能力。 
**/
package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithmRef;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估算法编辑响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalAlgorithmEditRsp extends EvalAlgorithmRef {

	/**
	 * 靶场
	 */
	@ApiModelProperty(value = "算法矩阵内容一致性")
	private boolean cr;

	/**
	 * 味精
	 */
	@ApiModelProperty(value = "算法矩阵内容一致性返回信息")
	private String msg;
}
