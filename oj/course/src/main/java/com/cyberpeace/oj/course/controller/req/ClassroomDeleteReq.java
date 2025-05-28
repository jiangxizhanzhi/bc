/**
 * 这段代码定义了一个名为 `ClassroomDeleteReq` 的 Java 类，用于表示课堂删除请求的数据模型。以下是详细的分析： 
 * ### 包声明 
 * ```java 
 * package com.cyberpeace.oj.course.controller.req; 
 * ``` 
 * 该类位于 `com.cyberpeace.oj.course.controller.req` 包中。 
 * ### 导入的包 
 * ```java 
 * import io.swagger.annotations.ApiModel; 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * import javax.validation.constraints.NotBlank; 
 * import java.io.Serializable; 
 * ``` 
 * - `io.swagger.annotations.ApiModel` 和 `io.swagger.annotations.ApiModelProperty`：用于生成 API 文档。 
 * - `lombok.Getter` 和 `lombok.Setter`：用于自动生成 getter 和 setter 方法。 
 * - `javax.validation.constraints.NotBlank`：用于验证字段是否为空或空白。 
 * - `java.io.Serializable`：表示该类可以序列化。 
 * ### 类声明 
 * ```java 
 * @Getter 
 * @Setter 
 * @ApiModel 
 * public class ClassroomDeleteReq implements Serializable { 
 * ``` 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解自动生成 getter 和 setter 方法。 
 * - `@ApiModel` 注解用于描述这个类在 Swagger 中的表现形式。 
 * - 实现 `Serializable` 接口，使对象可以序列化。 
 * ### 字段声明 
 * ```java 
 * @ApiModelProperty("课堂编号") 
 * @NotBlank 
 * private String resourceId; 
 * ``` 
 * - `resourceId` 字段用于存储课堂编号。 
 * - `@ApiModelProperty` 注解用于描述该字段在 Swagger 文档中的描述信息。 
 * - `@NotBlank` 注解用于确保 `resourceId` 字段不为空或空白。 
 * ### 总结 
 * - `ClassroomDeleteReq` 类用于处理课堂删除请求的数据模型。 
 * - 它包含一个名为 `resourceId` 的字段，用于存储课堂编号，并且必须是非空字符串。 
 * - 该类使用 Lombok 注解简化了代码，实现了自动的 getter 和 setter 方法。 
 * - 通过 Swagger 注解，该类可以在 API 文档中正确展示。 
 * 这个类的主要目的是作为请求体传递课堂编号给后端进行删除操作，并且确保传入的课堂编号不为空。 
**/
package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 课堂删除请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class ClassroomDeleteReq implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("课堂编号")
	@NotBlank
	private String resourceId;

}
