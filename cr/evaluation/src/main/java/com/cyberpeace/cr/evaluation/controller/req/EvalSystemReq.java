package com.cyberpeace.cr.evaluation.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.evaluation.service.context.IndexContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估系统请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalSystemReq` 的 Java 类，该类用于表示评估系统的请求体。以下是详细的分析和总结： 
 * ### 类定义和注解 
 * - **包声明**：`com.cyberpeace.cr.evaluation.controller.req` 
 * - **导入的包**： 
 * - `java.util.List` 
 * - `javax.validation.constraints.NotNull` 
 * - `io.swagger.annotations.ApiModelProperty` 
 * - `lombok.Getter` 和 `lombok.Setter` 
 * ### 类属性 
 * - **evalSystemId**：评估系统的唯一标识符，使用 `@NotNull` 注解确保其不为空。 
 * - **name**：评估系统的名称，使用 `@NotNull` 注解确保其不为空。 
 * - **description**：评估系统的描述，可以为空。 
 * - **connlist**：连接列表，使用 `@NotNull` 注解确保其不为空。 
 * - **firstIndexes**：一级指标列表，使用 `@NotNull` 注解确保其不为空。 
 * - **secondIndexes**：二级指标列表，使用 `@NotNull` 注解确保其不为空。 
 * - **thirdIndexes**：三级指标列表，使用 `@NotNull` 注解确保其不为空。 
 * ### 类注释 
 * - 类上的 `@Getter` 和 `@Setter` 注解来自 Lombok 库，自动生成 getter 和 setter 方法。 
 * - 类上的 Javadoc 文档说明了该类的作用。 
 * ### 字段注释 
 * - 每个字段都有对应的 Javadoc 文档，说明了字段的用途。 
 * ### 总结 
 * - **功能**：`EvalSystemReq` 类用于封装评估系统相关的请求数据，包括评估系统 ID、名称、描述、连接列表以及各级指标列表。 
 * - **验证**：通过 `@NotNull` 注解确保一些关键字段（如 `evalSystemId`、`name`、`connlist`、`firstIndexes`、`secondIndexes`、`thirdIndexes`）在请求时必须提供。 
 * - **Swagger 文档**：通过 `@ApiModelProperty` 注解为 Swagger 提供文档信息，方便 API 文档生成。 
 * 这个类的设计简洁明了，易于理解，并且通过注解实现了必要的数据验证。 
**/
@Getter
@Setter
public class EvalSystemReq {

	/**
	 * 评估系统id
	 */
	@ApiModelProperty(value = "评估体系Id", required = true)
	@NotNull
	private String evalSystemId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述", required = false)
	private String description;

	/**
	 * 连接列表
	 */
	@ApiModelProperty(value = "连接线", required = true)
	@NotNull
	private String connlist;

	/**
	 * 第一个索引
	 */
	@ApiModelProperty(value = "一级指标列表", required = true)
	@NotNull
	private List<IndexContext> firstIndexes;

	/**
	 * 第二个索引
	 */
	@ApiModelProperty(value = "二级指标列表", required = true)
	@NotNull
	private List<IndexContext> secondIndexes;

	/**
	 * 第三索引
	 */
	@ApiModelProperty(value = "三级指标列表", required = true)
	@NotNull
	private List<IndexContext> thirdIndexes;

}
