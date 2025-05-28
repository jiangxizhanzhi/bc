/**
 * 这段代码定义了一个名为 `EvalIndicatorContext` 的 Java 类，该类位于 `com.cyberpeace.cr.evaluation.service.context` 包中。以下是对其主要特性和功能的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.cr.evaluation.service.context` 
 * - **类名**: `EvalIndicatorContext` 
 * - **作者**: SN 
 * - **创建日期**: 2024年11月8日 
 * ### 功能描述 
 * 这个类主要用于表示评估指标（EVAL）的上下文信息，包括评估指示器的ID、名称、聚合算法及其参数等。 
 * ### 主要属性 
 * - **evalIndicatorId**: 评估指示器的唯一标识符。 
 * - **name**: 评估指示器的名称。 
 * - **aggregatingAlgorithmId**: 聚合算法的唯一标识符。 
 * - **aggregatingAlgorithmParams**: 聚合算法的参数。 
 * - **aggregationIds**: 与当前评估指示器相关的聚合项ID列表。 
 * - **collectionIds**: 与当前评估指示器相关的收集项ID列表。 
 * - **matrixData**: 层次分析法（AHP）的JSON数据。 
 * ### 注解 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * - 使用了 `javax.validation.constraints.NotNull` 注解，确保某些字段不能为空。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，用于生成 API 文档。 
 * ### 总结 
 * 这个类是一个典型的 POJO (Plain Old Java Object)，主要用于存储和传输评估指标的相关信息。它包含了评估指标的基本信息以及与其相关联的数据结构，如聚合项和收集项的 ID 列表。通过使用 Lombok 和 Swagger 注解，简化了代码并增强了可读性及文档生成能力。 
**/
package com.cyberpeace.cr.evaluation.service.context;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * EVAL 指标上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalIndicatorContext {

	/**
	 * 评估指示器id
	 */
	@ApiModelProperty(value = "指标项id", required = true)
	@NotNull
	private String evalIndicatorId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	/**
	 * 聚合算法id
	 */
	@ApiModelProperty(value = "聚合算法id", required = true)
	@NotNull
	private String aggregatingAlgorithmId;

	/**
	 * 聚合算法参数
	 */
	@ApiModelProperty(value = "聚合算法参数", required = true)
	@NotNull
	private String aggregatingAlgorithmParams;

	/**
	 * 集合体id列表
	 */
	@ApiModelProperty(value = "关联聚合项id", required = true)
	@NotNull
	private List<String> aggregationIds;

	/**
	 * 收集id列表
	 */
	@ApiModelProperty(value = "关联采集项id", required = false)
	@NotNull
	private List<String> collectionIds;

	/**
	 * 矩阵数据
	 */
	@ApiModelProperty(value = "层次分析法json数组", required = false)
	private String matrixData;

}
