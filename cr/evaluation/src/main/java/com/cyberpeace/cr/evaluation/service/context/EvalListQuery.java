/**
 * 这段代码定义了一个名为 `EvalListQuery` 的 Java 类，用于表示评估列表查询的请求参数。以下是详细的分析： 
 * ### 包声明和导入 
 * - **包声明**：`com.cyberpeace.cr.evaluation.service.context` 
 * - 表示这个类位于 `com.cyberpeace.cr.evaluation.service.context` 包下。 
 * - **导入的类和注解**： 
 * - `PageReqBase`：可能是一个分页请求的基本类。 
 * - `ApiModel` 和 `ApiModelProperty`：来自 Swagger 注解库，用于生成 API 文档。 
 * - `Getter` 和 `Setter`：来自 Lombok 库，简化了属性的 getter 和 setter 方法的生成。 
 * ### 类声明 
 * - **类名**：`EvalListQuery` 
 * - 继承自 `PageReqBase` 类。 
 * - 使用 `@ApiModel` 注解，说明这是一个用于 API 文档的模型类。 
 * - 使用 `@Getter` 和 `@Setter` 注解，自动生成所有属性的 getter 和 setter 方法。 
 * ### 属性 
 * - **name** 
 * - 类型：`String` 
 * - 用途：表示评估项目的名称。 
 * - 注解：`@ApiModelProperty(value = "名称")` 
 * - **type** 
 * - 类型：`Integer` 
 * - 用途：表示评估项目类型。 
 * - 注解：`@ApiModelProperty(value = "类型")` 
 * - **apply_type** 
 * - 类型：`String` 
 * - 用途：表示算法应用类型（可选）。 
 * - 注解：`@ApiModelProperty(value = "算法应用类型", required = false)` 
 * - **algorithm_language** 
 * - 类型：`Integer` 
 * - 用途：表示算法使用的编程语言（0: Python, 1: Java）。 
 * - 注解：`@ApiModelProperty(value = "算法语言 0:python,1:java")` 
 * - **ids** 
 * - 类型：`List<String>` 
 * - 用途：表示一组评估项目的 ID 列表。 
 * - 注解：无 
 * ### 总结 
 * - `EvalListQuery` 类主要用于构建一个查询条件对象，包含名称、类型、算法应用类型、算法语言和 ID 列表等属性。 
 * - 这个类继承了 `PageReqBase`，可能是为了支持分页查询。 
 * - 使用 Swagger 注解来生成 API 文档，并且通过 Lombok 注解简化了代码量。 
 * - 该类适用于前台使用，用于向后端发送查询请求。 
 * ### 适用场景 
 * - 这个类通常在前端需要获取某个评估项目的列表时使用，可以通过不同的查询条件（如名称、类型等）来过滤结果。 
**/
package com.cyberpeace.cr.evaluation.service.context;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 评估列表查询
 *
 * @author SN
 * @date 2024/11/08
 *//*
 * 前台使用的查询类
 */
@ApiModel
@Getter
@Setter
public class EvalListQuery extends PageReqBase {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	private Integer type;

	@ApiModelProperty(value = "算法应用类型", required = false)
	private String apply_type;

	@ApiModelProperty(value = "算法语言 0:python,1:java")
	private Integer algorithm_language;

	/**
	 * id列表
	 */
	private List<String> ids;

}