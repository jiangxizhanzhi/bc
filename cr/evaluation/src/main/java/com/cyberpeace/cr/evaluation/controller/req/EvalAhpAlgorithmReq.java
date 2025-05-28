/**
 * 这段代码定义了一个名为 `EvalAhpAlgorithmReq` 的 Java 类，用于表示评估 AHP（Analytic Hierarchy Process）算法的请求体。以下是该类的详细分析： 
 * ### 包声明和导入 
 * - **包声明**：`com.cyberpeace.cr.evaluation.controller.req` 
 * - 表示该类位于 `com.cyberpeace.cr.evaluation.controller.req` 包中。 
 * - **导入**： 
 * - `io.swagger.annotations.ApiModelProperty`：用于 Swagger 文档生成，标注字段在 API 文档中的描述。 
 * - `lombok.Getter` 和 `lombok.Setter`：Lombok 注解，自动生成 getter 和 setter 方法。 
 * ### 类声明 
 * - **类名**：`EvalAhpAlgorithmReq` 
 * - 这个类的主要目的是封装评估 AHP 算法所需的数据。 
 * ### 类注释 
 * - 通过 `@Getter` 和 `@Setter` 注解，Lombok 会为类中的所有属性自动生成 getter 和 setter 方法。 
 * ### 属性 
 * - **evalAlgorithmId** 
 * - 类型：`String` 
 * - 注释：用于标识评估算法的 ID。 
 * - 标注：`@ApiModelProperty(value = "算法Id", required = false)`，表示该字段在 Swagger 文档中被标记为可选。 
 * - **matrixData** 
 * - 类型：`String` 
 * - 注释：用于存储层次分析法的 JSON 数据。 
 * - 标注：`@ApiModelProperty(value = "层次分析法json数组", required = true)`，表示该字段在 Swagger 文档中被标记为必填。 
 * ### 总结 
 * - **用途**：`EvalAhpAlgorithmReq` 类主要用于接收和封装评估 AHP 算法所需的参数，包括算法 ID 和层次分析法的 JSON 数据。 
 * - **特性**：使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来简化代码，并且通过 Swagger 的 `@ApiModelProperty` 注解来生成 API 文档。 
 * 这个类的设计简洁明了，符合 RESTful API 设计的最佳实践。 
**/
package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估AHP算法请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalAhpAlgorithmReq {

	/**
	 * 评估算法id
	 */
	@ApiModelProperty(value = "算法Id", required = false)
	private String evalAlgorithmId;

	/**
	 * 矩阵数据
	 */
	@ApiModelProperty(value = "层次分析法json数组", required = true)
	private String matrixData;
}
