package com.cyberpeace.cr.evaluation.service.context;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * eval 聚合上下文
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalAggregationContext` 的 Java 类，该类位于包 `com.cyberpeace.cr.evaluation.service.context` 中。这个类主要用于表示评估聚合上下文的信息。以下是对其主要特性和功能的简要分析： 
 * ### 包声明与导入 
 * - **包声明**：代码的第一行指定了该类所在的包 `com.cyberpeace.cr.evaluation.service.context`。 
 * - **导入语句**：导入了 `javax.validation.constraints.NotNull` 用于注解非空验证，以及 `io.swagger.annotations.ApiModelProperty` 用于生成 API 文档。 
 * ### 类定义 
 * - **注解**： 
 * - 使用 `@Getter` 和 `@Setter` 注解自动生成属性的 getter 和 setter 方法。 
 * - 使用 `@ApiModelProperty` 注解来为每个字段添加描述信息，以便在 Swagger 生成的 API 文档中显示这些信息。 
 * - **字段**： 
 * - `evalAggregationId`：表示评估聚合项的唯一标识符，类型为 `String`。 
 * - `name`：表示名称，类型为 `String`。 
 * - `evalIndicatorId`：表示评估指示器的唯一标识符，类型为 `String`。 
 * - `aggregatingAlgorithmId`：表示聚合算法的唯一标识符，类型为 `String`。 
 * - `aggregatingAlgorithmParams`：表示聚合算法的参数，类型为 `String`。 
 * - `aggregationIds`：表示关联的聚合项ID列表，类型为 `List<String>`。 
 * - `matrixData`：表示矩阵数据（层次分析法的 JSON 数组），类型为 `String`，并且可以为空。 
 * ### 总结 
 * - **用途**：`EvalAggregationContext` 类用于封装评估聚合上下文的信息，包括聚合项ID、名称、评估指示器ID、聚合算法ID及其参数、关联的聚合项ID列表和矩阵数据。 
 * - **注解使用**：通过使用 Lombok 提供的 `@Getter` 和 `@Setter` 注解，简化了代码量，同时使用 `@NotNull` 和 `@ApiModelProperty` 注解确保了字段的非空验证和文档生成。 
 * - **设计目的**：该类的设计目的是为了在系统内部或外部接口中传递和处理评估聚合相关的数据，特别是用于支持评估和分析过程中的聚合操作。 
**/
@Getter
@Setter
public class EvalAggregationContext {

	/**
	 * 评估集合体id
	 */
	@ApiModelProperty(value = "聚合项id", required = true)
	@NotNull
	private String evalAggregationId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	/**
	 * 评估指示器id
	 */
	@ApiModelProperty(value = "指标项id", required = true)
	@NotNull
	private String evalIndicatorId;

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
	@ApiModelProperty(value = "关联采集变量id(前端统一命名，需要转换一下存数据库)", required = true)
	@NotNull
	private List<String> aggregationIds;

	/**
	 * 矩阵数据
	 */
	@ApiModelProperty(value = "层次分析法json数组", required = false)
	private String matrixData;

}

