/**
 * 这段代码定义了一个名为 `EvalIndicatorRsp` 的 Java 类，用于表示评估指示器的响应。该类使用了 Lombok 库来自动生成 getter 和 setter 方法，并且使用了 Swagger 注解来描述 API 接口中的字段。以下是对该类的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.cr.evaluation.controller.rsp` 
 * - **类名**: `EvalIndicatorRsp` 
 * - **功能**: 用于表示评估指示器的响应数据结构。 
 * ### 主要特性 
 * 1. **Lombok 注解**: 
 * - `@Getter`: 自动生成所有字段的 getter 方法。 
 * - `@Setter`: 自动生成所有字段的 setter 方法。 
 * 2. **Swagger 注解**: 
 * - `@ApiModelProperty`: 用于描述 API 中的字段，包括字段的名称、是否必需等信息。 
 * 3. **字段**: 
 * - `evalIndicatorId`: 评估指示器 ID，类型为字符串，必须提供。 
 * - `name`: 指示器的名称，类型为字符串，必须提供。 
 * - `description`: 指示器的描述，类型为字符串，可选。 
 * - `attribute`: 指标属性，类型为整数，必须提供。 
 * - `attributeValue`: 指标属性值，类型为整数，可选。 
 * - `attributeVal`: 指标属性值，类型为字符串，可选。 
 * - `datatype`: 数据类型，类型为整数，必须提供。 
 * - `connlist`: 连接列表，类型为字符串，必须提供。 
 * - `evalCollections`: 评估集合项列表，类型为 `EvalCollectionContext` 列表，必须提供。 
 * - `evalAggregations`: 评估聚合项列表，类型为 `EvalAggregationContext` 列表，必须提供。 
 * - `evalIndicators`: 评估指示器列表，类型为 `EvalIndicatorContext` 列表，必须提供。 
 * - `evalAggregationNum`: 评估聚合项数量，类型为整数，必须提供。 
 * - `evalCollectionNum`: 评估收集数量，类型为整数，必须提供。 
 * ### 总结 
 * - `EvalIndicatorRsp` 类是一个数据传输对象（DTO），用于在评估系统中传递评估指示器的相关信息。 
 * - 该类通过 Lombok 和 Swagger 注解简化了代码的编写，并提供了清晰的 API 文档说明。 
 * - 字段的注释详细描述了每个字段的用途和要求，使得该类易于理解和使用。 
 * 这个类的设计很好地满足了评估指示器响应数据的结构化需求，并且通过注解使得 API 文档更加清晰。 
**/
package com.cyberpeace.cr.evaluation.controller.rsp;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.evaluation.service.context.EvalAggregationContext;
import com.cyberpeace.cr.evaluation.service.context.EvalCollectionContext;
import com.cyberpeace.cr.evaluation.service.context.EvalIndicatorContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估指示器响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalIndicatorRsp {

	/**
	 * 评估指示器id
	 */
	@ApiModelProperty(value = "指标项Id", required = true)
	@NotNull
	private String evalIndicatorId;

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
	 * 属性
	 */
	@ApiModelProperty(value = "指标属性", required = true)
	@NotNull
	private Integer attribute;

	/**
	 * 属性值
	 */
	@ApiModelProperty(value = "指标属性值", required = false)
	private Integer attributeValue;

	/**
	 * 属性 val
	 */
	@ApiModelProperty(value = "指标属性值", required = false)
	private String attributeVal;

	/**
	 * 数据类型
	 */
	@ApiModelProperty(value = "数据类型", required = true)
	@NotNull
	private Integer datatype;

	/**
	 * 连接列表
	 */
	@ApiModelProperty(value = "连接线", required = true)
	@NotNull
	private String connlist;

	/**
	 * EVAL 集合
	 */
	@ApiModelProperty(value = "采集项列表", required = true)
	@NotNull
	private List<EvalCollectionContext> evalCollections;

	/**
	 * eval 聚合
	 */
	@ApiModelProperty(value = "聚合项列表", required = true)
	@NotNull
	private List<EvalAggregationContext> evalAggregations;

	/**
	 * 评估指示器
	 */
	@ApiModelProperty(value = "指标项列表", required = true)
	@NotNull
	private List<EvalIndicatorContext> evalIndicators;

	/**
	 * 评估集合体数量
	 */
	@ApiModelProperty(value = "聚合项数量", required = true)
	@NotNull
	private Integer evalAggregationNum;

	/**
	 * 评估收集数量
	 */
	@ApiModelProperty(value = "采集项数量", required = true)
	@NotNull
	private Integer evalCollectionNum;
}
