package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.cr.evaluation.service.context.AlgorithmParamContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 评估算法请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalAlgorithmReq` 的 Java 类，用于表示评估算法请求体。以下是对该类的简要分析： 
 * ### 包与导入 
 * - **包声明**：`com.cyberpeace.cr.evaluation.controller.req` 
 * - **导入**： 
 * - `com.cyberpeace.cr.evaluation.service.context.AlgorithmParamContext`：用于表示算法参数。 
 * - `io.swagger.annotations.ApiModelProperty`：用于在 Swagger 文档中描述字段。 
 * - `lombok.Getter` 和 `lombok.Setter`：Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * - `org.apache.commons.compress.utils.Lists`：用于创建不可变列表。 
 * ### 类定义 
 * - **注解**： 
 * - `@Getter` 和 `@Setter`：Lombok 注解，自动为所有字段生成 getter 和 setter 方法。 
 * - `@ApiModelProperty`：用于描述字段在 Swagger 文档中的信息。 
 * - `@NotNull`：JSR 380 (Bean Validation API) 注解，确保字段不为空。 
 * ### 字段 
 * - `evalAlgorithmId`：字符串类型的评估算法 ID，可选。 
 * - `name`：字符串类型的名称，必填。 
 * - `description`：字符串类型的基本原理，可选。 
 * - `instructions`：字符串类型的使用说明，可选。 
 * - `advantage`：字符串类型的算法优势，可选。 
 * - `applyType`：字符串类型的列表，表示算法应用类型，必填。 
 * - `type`：整数类型的算法类型，必填。 
 * - `algorithmLanguage`：整数类型的算法语言，必填（0 表示 Python，1 表示 Java）。 
 * - `code`：字符串类型的代码，必填。 
 * - `mode`：整数类型的模式，必填。 
 * - `builtin`：整数类型，表示是否为内置算法，必填。 
 * - `params`：`AlgorithmParamContext` 类型的列表，表示参数列表，必填且默认初始化为一个空列表。 
 * ### 总结 
 * - `EvalAlgorithmReq` 类主要用于封装评估算法请求所需的信息。 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解简化了代码。 
 * - 使用 JSR 380 的 `@NotNull` 注解确保某些字段的非空性。 
 * - 使用 Swagger 的 `@ApiModelProperty` 注解来提供字段的文档描述。 
 * - 类中的字段涵盖了评估算法的各种属性，如名称、类型、代码等，并且通过注解确保了数据的有效性和文档的可读性。 
**/
@Getter
@Setter
public class EvalAlgorithmReq {

	/**
	 * 评估算法id
	 */
	@ApiModelProperty(value = "算法Id", required = false)
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

	@ApiModelProperty("算法应用类型, type=56")
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

}

