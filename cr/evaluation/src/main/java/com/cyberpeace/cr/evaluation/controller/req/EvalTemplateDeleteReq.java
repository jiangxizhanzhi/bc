/**
 * 这段代码定义了一个名为 `EvalTemplateDeleteReq` 的 Java 类，该类用于表示删除评估模板请求的参数。以下是对这段代码的简要分析： 
 * ### 包声明 
 * ```java 
 * package com.cyberpeace.cr.evaluation.controller.req; 
 * ``` 
 * - 该类位于 `com.cyberpeace.cr.evaluation.controller.req` 包下。 
 * ### 导入的类 
 * ```java 
 * import java.util.List; 
 * import javax.validation.constraints.NotEmpty; 
 * import javax.validation.constraints.NotNull; 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * ``` 
 * - `List`: 用于表示模板ID列表。 
 * - `@NotEmpty`, `@NotNull`: 用于验证字段是否为空。 
 * - `@ApiModelProperty`: 用于Swagger文档注解，描述API模型属性。 
 * - `@Getter`, `@Setter`: Lombok注解，自动生成getter和setter方法。 
 * ### 类声明 
 * ```java 
 * @Getter 
 * @Setter 
 * public class EvalTemplateDeleteReq { 
 * ``` 
 * - 使用 Lombok 注解 `@Getter` 和 `@Setter` 自动生成 getter 和 setter 方法。 
 * - 类名 `EvalTemplateDeleteReq` 表示评估模板删除请求体。 
 * ### 类成员变量 
 * ```java 
 * @ApiModelProperty(value = "靶场测评模板Ids", required = true) 
 * @NotNull 
 * @NotEmpty 
 * private List<String> templateIds; 
 * ``` 
 * - `templateIds`: 一个字符串列表，表示需要删除的模板ID。 
 * - `@ApiModelProperty`: 描述这个字段在Swagger文档中的名称为“靶场测评模板Ids”，并且是必填项。 
 * - `@NotNull`: 确保该字段不为null。 
 * - `@NotEmpty`: 确保该字段不为空（即列表中至少有一个元素）。 
 * ### 总结 
 * - 这个类主要用于接收删除评估模板请求时传递的模板ID列表。 
 * - 字段 `templateIds` 是必需的，并且不能为空。 
 * - 使用了 Lombok 来简化代码，自动生成getter和setter方法。 
 * - 通过 Swagger 注解来生成API文档，使得接口更加易于理解和使用。 
**/
package com.cyberpeace.cr.evaluation.controller.req;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估模板删除请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalTemplateDeleteReq {

	/**
	 * 模板id列表
	 */
	@ApiModelProperty(value = "靶场测评模板Ids", required = true)
	@NotNull
	@NotEmpty
	private List<String> templateIds;

}