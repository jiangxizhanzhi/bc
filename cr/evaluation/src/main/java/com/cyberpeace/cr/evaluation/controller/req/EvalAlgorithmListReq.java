package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 评估算法列表请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalAlgorithmListReq` 的 Java 类，该类用于表示评估算法列表的请求体。以下是对该代码的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.cr.evaluation.controller.req` 
 * - **继承关系**: 该类继承自 `PageReqBase` 并实现了 `Serializable` 接口。 
 * ### 类注释 
 * - 类注释说明了该类的作用是表示“评估算法列表请求体”。 
 * - 注释中还提供了作者和日期信息。 
 * ### 类属性 
 * - **type**: 一个整数类型的变量，用于表示算法的类型。注释说明其为可选参数（非必需）。 
 * - **apply_type**: 一个字符串类型的变量，用于表示算法的应用类型。注释说明其为可选参数（非必需）。 
 * - **algorithm_language**: 一个整数类型的变量，用于表示算法使用的编程语言。注释说明了具体的编码值（0 表示 Python，1 表示 Java）。 
 * ### 使用的注解 
 * - **@Getter** 和 **@Setter**: Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * - **@ApiModelProperty**: Swagger 注解，用于在 API 文档中描述这些字段的用途和必要性。 
 * ### 总结 
 * 这个类主要用于封装从客户端传来的关于评估算法列表的查询条件，包括算法类型、应用类型以及编程语言等信息。通过继承 `PageReqBase` 类，它可能还包含了分页相关的参数。使用 Lombok 注解简化了代码量，并且通过 Swagger 注解增强了 API 文档的可读性和可用性。 
**/
@Getter
@Setter
public class EvalAlgorithmListReq extends PageReqBase implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 类型
	 */
	@ApiModelProperty(value = "算法类型", required = false)
	private Integer type;

	@ApiModelProperty(value = "算法应用类型", required = false)
	private String apply_type;

	@ApiModelProperty(value = "算法语言 0:python,1:java")
	private Integer algorithm_language;
}

