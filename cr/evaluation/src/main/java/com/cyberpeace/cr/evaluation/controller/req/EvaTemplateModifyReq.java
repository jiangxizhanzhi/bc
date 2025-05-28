/**
 * 这段代码定义了一个名为 `EvaTemplateModifyReq` 的 Java 类，它是 `EvaTemplateAddReq` 类的子类。以下是对其主要特性的简要分析： 
 * 1. **包声明**： 
 * - 这个类位于 `com.cyberpeace.cr.evaluation.controller.req` 包下。 
 * 2. **导入的库**： 
 * - `javax.validation.constraints.NotEmpty`：用于确保字段不为空。 
 * - `io.swagger.annotations.ApiModelProperty`：用于在 Swagger 文档中描述 API 模型属性。 
 * - `lombok.Getter` 和 `lombok.Setter`：Lombok 注解，自动生成 getter 和 setter 方法。 
 * 3. **类注释**： 
 * - 类注释说明了该类是“伊娃模板修改请求体”。 
 * 4. **继承关系**： 
 * - `EvaTemplateModifyReq` 继承自 `EvaTemplateAddReq`。 
 * 5. **字段**： 
 * - `resourceId`：资源 ID 字段，类型为 `String`。 
 * - `@ApiModelProperty` 注解表明该字段在 Swagger 文档中的描述为“测评靶场模板id”，并且是必需的。 
 * - `@NotEmpty` 注解确保 `resourceId` 字段不能为空。 
 * 6. **Getter 和 Setter 方法**： 
 * - `@Getter` 和 `@Setter` 注解由 Lombok 自动生成，无需手动编写这些方法。 
 * 总结来说，这个类主要用于表示用于修改伊娃模板的请求数据，其中包含一个必需的资源 ID 字段，并且继承了另一个与添加模板相关的请求类。 
**/
package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 伊娃模板修改请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvaTemplateModifyReq extends EvaTemplateAddReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "测评靶场模板id", required = true)
	@NotEmpty
	private String resourceId;

}