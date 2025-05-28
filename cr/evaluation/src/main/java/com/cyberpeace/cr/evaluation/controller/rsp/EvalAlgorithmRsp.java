/**
 * 这段代码定义了一个名为 `EvalAlgorithmRsp` 的 Java 类，该类用于表示评估算法的响应信息。以下是对代码的简要分析： 
 * ### 包声明和导入 
 * - **包声明**：`com.cyberpeace.cr.evaluation.controller.rsp` 
 * - **导入语句**： 
 * - `com.cyberpeace.cr.evaluation.service.context.AlgorithmParamContext`：表示算法参数上下文。 
 * - `io.swagger.annotations.ApiModelProperty`：用于生成 Swagger 文档中的注解。 
 * - `lombok.Getter` 和 `lombok.Setter`：用于自动生成 getter 和 setter 方法。 
 * - `org.apache.commons.compress.utils.Lists`：用于创建不可变列表。 
 * - `javax.validation.constraints.NotNull`：用于确保字段不为空。 
 * ### 类声明 
 * - 类名：`EvalAlgorithmRsp` 
 * - 注解： 
 * - `@Getter` 和 `@Setter`：由 Lombok 自动生成 getter 和 setter 方法。 
 * - 类描述：`评估算法响应` 
 * ### 字段和注解 
 * - `evalAlgorithmId`：评估算法ID，字符串类型，必填。 
 * - `name`：名称，字符串类型，必填。 
 * - `description`：描述，字符串类型。 
 * - `instructions`：使用说明，字符串类型。 
 * - `advantage`：算法优势，字符串类型。 
 * - `applyType`：算法应用类型，字符串列表类型。 
 * - `type`：算法类型，整型，必填。 
 * - `algorithmLanguage`：算法语言，整型，必填。 
 * - `code`：代码，字符串类型，必填。 
 * - `matrixData`：矩阵数据，字符串类型。 
 * - `mode`：模式，整型，必填。 
 * - `builtin`：是否为内置算法，整型，必填。 
 * - `params`：参数列表，`AlgorithmParamContext` 列表类型，初始值为一个空的不可变列表。 
 * - `paramsNum`：参数个数，整型，必填。 
 * ### 总结 
 * - 这个类主要用于封装评估算法的响应信息，包括算法的基本信息、参数等。 
 * - 使用了 Lombok 来简化代码，避免手动编写 getter 和 setter 方法。 
 * - 字段通过 Swagger 注解进行文档化，方便接口调用者了解每个字段的用途和约束条件。 
 * - 字段多数被标记为必填项（`@NotNull`），确保数据完整性。 
**/
package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.evaluation.service.context.AlgorithmParamContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 评估算法响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalAlgorithmRsp {

	/**
	 * 评估算法id
	 */
	@ApiModelProperty(value = "算法Id", required = true)
	@NotNull
	private String evalAlgorithmId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty("基本原理")
	private String description;

	@ApiModelProperty("使用说明")
	private String instructions;

	@ApiModelProperty("运用优势")
	private String advantage;

	@ApiModelProperty("算法应用类型,type=56")
	private List<String> applyType;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "算法类型", required = true)
	@NotNull
	private Integer type;

	/**
	 * 算法语言
	 */
	@ApiModelProperty(value = "算法语言 0:python,1:java", required = true)
	@NotNull
	private Integer algorithmLanguage;

	/**
	 * 代号
	 */
	@ApiModelProperty(value = "代码", required = true)
	@NotNull
	private String code;

	/**
	 * 矩阵数据
	 */
	@ApiModelProperty(value = "矩阵代码", required = true)
	private String matrixData;

	/**
	 * 模式
	 */
	@ApiModelProperty(value = "算法类型", required = true)
	@NotNull
	private Integer mode;

	/**
	 * 内置
	 */
	@ApiModelProperty(value = "是否为内置算法", required = true)
	@NotNull
	private Integer builtin;

	/**
	 * 参数
	 */
	@ApiModelProperty(value = "参数列表", required = true)
	@NotNull
	private List<AlgorithmParamContext> params = Lists.newArrayList();

	/**
	 * 参数数量
	 */
	@ApiModelProperty(value = "参数个数", required = true)
	@NotNull
	private Integer paramsNum;

}
