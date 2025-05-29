package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 评估系统变量结果响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalSystemVariableResultRsp` 的 Java 类，用于表示评估系统变量的结果。以下是该类的详细分析： 
 * ### 类名和包声明 
 * - **包声明**：`package com.cyberpeace.cr.evaluation.controller.rsp;` 
 * - 这个类位于 `com.cyberpeace.cr.evaluation.controller.rsp` 包中。 
 * - **类名**：`EvalSystemVariableResultRsp` 
 * - 类名使用了驼峰命名法，并且具有描述性，表明这是一个与评估系统变量结果相关的响应类。 
 * ### 注解 
 * - **Lombok 注解** 
 * - `@Getter` 和 `@Setter` 注解来自 Lombok 库，自动生成了所有字段的 getter 和 setter 方法。 
 * - **Swagger 注解** 
 * - `@ApiModelProperty` 注解来自 Swagger 库，用于生成 API 文档，为每个字段提供描述信息。 
 * ### 字段 
 * - **name**： 
 * - 类型：`String` 
 * - 描述：评估系统的变量名称。 
 * - **resourceId**： 
 * - 类型：`String` 
 * - 描述：资源 ID。 
 * - **exeCycle**： 
 * - 类型：`Integer` 
 * - 描述：执行轮次。 
 * - **firstScore**： 
 * - 类型：`Integer` 
 * - 描述：一级得分。 
 * - **secondScore**： 
 * - 类型：`Integer` 
 * - 描述：二级得分。 
 * - **thirdScore**： 
 * - 类型：`Integer` 
 * - 描述：三级得分。 
 * - **fourthScore**： 
 * - 类型：`Integer` 
 * - 描述：四级得分。 
 * - **fifthScore**： 
 * - 类型：`Integer` 
 * - 描述：五级得分。 
 * ### 总结 
 * - **功能**：这个类用于封装评估系统变量结果的信息，包括名称、资源 ID、执行轮次以及五个级别的得分。 
 * - **特性**：使用了 Lombok 注解简化了 getter 和 setter 方法的编写，并使用 Swagger 注解来辅助生成 API 文档。 
 * - **用途**：通常在后端服务中返回给前端或其他服务作为 JSON 格式的响应数据。 
 * 这段代码简洁明了，适用于 RESTful API 响应的数据传输对象（DTO）。 
**/
@Getter
@Setter
public class EvalSystemVariableResultRsp {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "ID")
	private String resourceId;

	/**
	 * exe 循环
	 */
	@ApiModelProperty(value = "执行轮次")
	private Integer exeCycle;

	/**
	 * 第一分
	 */
	@ApiModelProperty(value = "一级得分")
	private Integer firstScore;

	/**
	 * Second Score 第二分
	 */
	@ApiModelProperty(value = "二级得分")
	private Integer secondScore;

	/**
	 * 第三分
	 */
	@ApiModelProperty(value = "三级得分")
	private Integer thirdScore;

	/**
	 * 第四分
	 */
	@ApiModelProperty(value = "四级得分")
	private Integer fourthScore;

	/**
	 * 第五分
	 */
	@ApiModelProperty(value = "五级得分")
	private Integer fifthScore;

}
