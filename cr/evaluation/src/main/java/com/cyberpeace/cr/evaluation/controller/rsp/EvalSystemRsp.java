/**
 * 这段代码定义了一个名为 `EvalSystemRsp` 的 Java 类，用于表示评估系统的响应数据。以下是对该类的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.cr.evaluation.controller.rsp` 
 * - **功能**: 该类表示评估系统的响应信息。 
 * ### 主要属性 
 * 1. **evalSystemId** (String): 评估系统的唯一标识符。 
 * 2. **name** (String): 评估系统的名称。 
 * 3. **description** (String): 评估系统的描述（可选）。 
 * 4. **connlist** (String): 评估系统中的连接列表。 
 * 5. **firstIndexes** (List<IndexContext>): 一级指标列表。 
 * 6. **secondIndexes** (List<IndexContext>): 二级指标列表。 
 * 7. **thirdIndexes** (List<IndexContext>): 三级指标列表。 
 * ### 注解说明 
 * - **@Getter** 和 **@Setter**: Lombok 提供的注解，自动生成 getter 和 setter 方法。 
 * - **@ApiModelProperty**: Swagger 注解，用于生成 API 文档。 
 * - **@NotNull**: JSR 380 (Bean Validation) 标准注解，确保字段不为空。 
 * ### 特点 
 * - 所有主要属性都是必需的（使用 `@NotNull` 标记）。 
 * - 使用了 Lombok 库来简化代码，减少了样板代码。 
 * - 属性包括评估系统的基本信息及其相关的指标结构。 
 * ### 总结 
 * `EvalSystemRsp` 类是一个典型的 DTO（Data Transfer Object），用于在控制器层和外部系统之间传输评估系统的详细信息。该类通过 Lombok 和 Swagger 注解增强了其可读性和文档生成能力。 
**/
package com.cyberpeace.cr.evaluation.controller.rsp;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.evaluation.service.context.IndexContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估系统响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalSystemRsp {

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