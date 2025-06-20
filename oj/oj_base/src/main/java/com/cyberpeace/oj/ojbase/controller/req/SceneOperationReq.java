package com.cyberpeace.oj.ojbase.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 场景实操请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SceneOperationReq` 的 Java 类，用于表示场景实操请求体。以下是对该代码的简要分析： 
 * ### 包声明和导入 
 * - `package com.cyberpeace.oj.ojbase.controller.req;`：声明了类所在的包。 
 * - 导入了必要的类： 
 * - `java.io.Serializable`：接口，表示该类可以序列化。 
 * - `javax.validation.constraints.NotBlank`：注解，用于验证字符串不能为空或空白。 
 * - `io.swagger.annotations.ApiModel` 和 `io.swagger.annotations.ApiModelProperty`：注解，用于生成 Swagger 文档。 
 * - `lombok.Getter` 和 `lombok.Setter`：Lombok 注解，自动生成 getter 和 setter 方法。 
 * ### 类声明 
 * - `@ApiModel`：标注这个类是一个模型类，并且可以通过 Swagger 文档进行描述。 
 * - `@Getter` 和 `@Setter`：Lombok 注解，自动生成 `resourceId` 属性的 getter 和 setter 方法。 
 * - `public class SceneOperationReq implements Serializable`：定义了 `SceneOperationReq` 类实现 `Serializable` 接口。 
 * ### 属性 
 * - `@ApiModelProperty("课时、习题编号")`：标注该属性在 Swagger 文档中的描述。 
 * - `@NotBlank`：用于验证该属性不能为 null 或空白。 
 * - `private String resourceId;`：定义了一个私有字符串属性 `resourceId`，用于存储资源 ID。 
 * ### 总结 
 * - `SceneOperationReq` 类主要用于处理场景实操请求的数据。 
 * - 使用 Lombok 库简化了 getter 和 setter 方法的编写。 
 * - 使用 Swagger 注解来帮助生成 API 文档。 
 * - `resourceId` 属性是必需的（非空），并且会出现在 Swagger 文档中作为 "课时、习题编号"。 
 * 这个类的设计目的是接收一个包含资源 ID 的请求体，并确保该 ID 不为空。 
**/
@Getter
@Setter
@ApiModel
public class SceneOperationReq implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("课时、习题编号")
	@NotBlank
	private String resourceId;

}

