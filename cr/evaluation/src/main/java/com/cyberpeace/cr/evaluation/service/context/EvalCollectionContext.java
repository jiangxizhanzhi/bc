/**
 * 这段代码定义了一个名为 `EvalCollectionContext` 的 Java 类，该类用于表示评估集合的上下文信息。以下是对此代码的简要分析： 
 * ### 类名与包名 
 * - **包名**: `com.cyberpeace.cr.evaluation.service.context` 
 * - **类名**: `EvalCollectionContext` 
 * ### 类的注解 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成了所有字段的 getter 和 setter 方法。 
 * ### 字段说明 
 * 这个类包含以下字段： 
 * 1. `evalCollectionId`: 评估收集 ID。 
 * 2. `evalIndicatorId`: 评估指示器 ID。 
 * 3. `formatAlgorithmId`: 格式算法 ID。 
 * 4. `formatAlgorithmParams`: 格式算法参数。 
 * 5. `detectionAlgorithmId`: 检测算法 ID。 
 * 6. `detectionAlgorithmParams`: 检测算法参数。 
 * 7. `scoreAlgorithmId`: 评分算法 ID。 
 * 8. `scoreAlgorithmParams`: 评分算法参数。 
 * 9. `name`: 名称。 
 * ### 字段注解 
 * - 每个字段都使用了 `@NotNull` 注解，表示这些字段不能为空。 
 * - 每个字段都使用了 Swagger 的 `@ApiModelProperty` 注解，用于描述字段在 API 文档中的信息，例如字段名称、是否必需等。 
 * ### 总结 
 * - **用途**: 这个类主要用于存储与评估集合相关的各种标识符和参数信息。 
 * - **特性**: 所有字段都是必需的（非空），并且可以通过 Swagger 自动生成 API 文档。 
 * - **设计**: 通过 Lombok 减少了样板代码，提高了代码的可读性和维护性。 
 * 这个类的设计合理，适用于需要严格验证输入数据的场景，并且能够方便地生成 API 文档。 
**/
package com.cyberpeace.cr.evaluation.service.context;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * eval 集合上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalCollectionContext {

	/**
	 * 评估收集id
	 */
	@ApiModelProperty(value = "采集项id", required = true)
	@NotNull
	private String evalCollectionId;

	/**
	 * 评估指示器id
	 */
	@ApiModelProperty(value = "指标项id", required = true)
	@NotNull
	private String evalIndicatorId;

	/**
	 * 格式算法id
	 */
	@ApiModelProperty(value = "格式化算法id", required = true)
	@NotNull
	private String formatAlgorithmId;

	/**
	 * 格式算法参数
	 */
	@ApiModelProperty(value = "格式化算法参数", required = true)
	@NotNull
	private String formatAlgorithmParams;

	/**
	 * 检波算法id
	 */
	@ApiModelProperty(value = "检测算法id", required = true)
	@NotNull
	private String detectionAlgorithmId;

	/**
	 * 检测算法参数
	 */
	@ApiModelProperty(value = "检测算法参数", required = true)
	@NotNull
	private String detectionAlgorithmParams;

	/**
	 * 得分算法id
	 */
	@ApiModelProperty(value = "评分算法id", required = true)
	@NotNull
	private String scoreAlgorithmId;

	/**
	 * 评分算法参数
	 */
	@ApiModelProperty(value = "评分算法参数", required = true)
	@NotNull
	private String scoreAlgorithmParams;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

}
