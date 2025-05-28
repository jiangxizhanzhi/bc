/**
 * 这段代码定义了一个名为 `EvalTemplateIdRsp` 的 Java 类，用于表示评估模板ID的响应。以下是该类的主要特性和功能： 
 * 1. **包声明**：该类位于 `com.cyberpeace.cr.evaluation.controller.rsp` 包下。 
 * 2. **导入的类**： 
 * - `Serializable`：用于实现序列化接口。 
 * - `ApiModelProperty`：用于在 Swagger 文档中描述 API 属性。 
 * - `Getter` 和 `Setter`：来自 Lombok 库，用于自动生成 getter 和 setter 方法。 
 * 3. **注释**： 
 * - 类上的 Javadoc 注释说明了该类的作用，即提供评估模板ID的响应。 
 * - 类名和字段上也有 Javadoc 注释，解释了它们的用途。 
 * 4. **属性**： 
 * - `templateId`：表示测评靶场模板ID，类型为字符串。 
 * 5. **构造方法**： 
 * - 一个带有 `templateId` 参数的构造方法，用于初始化 `templateId` 字段。 
 * 6. **注解**： 
 * - `@ApiModelProperty`：用于描述 `templateId` 字段的用途。 
 * - `@Getter` 和 `@Setter`：自动为 `templateId` 字段生成 getter 和 setter 方法。 
 * ### 总结 
 * - `EvalTemplateIdRsp` 类实现了 `Serializable` 接口，便于对象的序列化。 
 * - 通过 Swagger 注解 (`@ApiModelProperty`) 描述了 API 中 `templateId` 字段的用途。 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解简化了代码，避免了手动编写 getter 和 setter 方法。 
 * 这个类主要用于封装评估模板ID的响应信息，并且可以通过序列化进行传输。 
**/
package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估模板id响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalTemplateIdRsp implements Serializable {

	/**
	 * 模板id
	 */
	@ApiModelProperty(value = "测评靶场模板id")
	private String templateId;

	public EvalTemplateIdRsp(String templateId) {
		this.templateId = templateId;
	}

}
