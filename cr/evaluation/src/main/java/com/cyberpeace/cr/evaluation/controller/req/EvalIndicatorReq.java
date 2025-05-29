package com.cyberpeace.cr.evaluation.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.evaluation.service.context.EvalAggregationContext;
import com.cyberpeace.cr.evaluation.service.context.EvalCollectionContext;
import com.cyberpeace.cr.evaluation.service.context.EvalIndicatorContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估指示器请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalIndicatorReq` 的 Java 类，该类用于表示评估指示器的请求体。以下是对此代码的简要分析： 
 * ### 包声明和导入 
 * - **包声明**：`com.cyberpeace.cr.evaluation.controller.req` 
 * - **导入的类**： 
 * - `java.util.List` 
 * - `javax.validation.constraints.NotNull` 
 * - 项目内部的 `EvalAggregationContext`, `EvalCollectionContext`, 和 `EvalIndicatorContext` 类 
 * - `io.swagger.annotations.ApiModelProperty` 
 * - `lombok.Getter` 和 `lombok.Setter` 
 * ### 类声明 
 * - 类名：`EvalIndicatorReq` 
 * - 注解： 
 * - `@Getter` 和 `@Setter`：由 Lombok 提供，自动生成 getter 和 setter 方法。 
 * - `@ApiModelProperty`：用于 Swagger 文档生成，描述每个字段。 
 * ### 字段和注解 
 * - `evalIndicatorId`：评估指示器 ID，必填字符串。 
 * - `name`：名称，必填字符串。 
 * - `datatype`：数据类型，必填整数。 
 * - `attribute`：属性，必填整数。 
 * - `attributeValue`：属性值，可选整数。 
 * - `description`：描述，可选字符串。 
 * - `connlist`：连接列表，必填字符串。 
 * - `evalCollections`：EVAL 集合，必填 `List<EvalCollectionContext>`。 
 * - `evalAggregations`：eval 聚合，必填 `List<EvalAggregationContext>`。 
 * - `evalIndicators`：评估指示器，必填 `List<EvalIndicatorContext>`。 
 * ### 总结 
 * - `EvalIndicatorReq` 类用于封装评估指示器请求所需的所有信息。 
 * - 使用了 Lombok 的注解简化了代码，减少了样板代码。 
 * - 字段使用了 `@NotNull` 和 `@ApiModelProperty` 注解来确保必填性和提供文档说明。 
 * - 该类主要用于接收前端或其他系统的请求，并传递给后端进行处理。 
 * 此代码结构清晰，使用了现代 Java 开发的最佳实践，如 Lombok 和 Swagger 注解。 
**/
@Getter
@Setter
public class EvalIndicatorReq {

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
	 * 数据类型
	 */
	@ApiModelProperty(value = "数据类型", required = true)
	@NotNull
	private Integer datatype;

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
}

