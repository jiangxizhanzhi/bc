package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 课堂标志在请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ClassroomSignInReq` 的 Java 类，用于处理课堂签到请求的数据传输对象（DTO）。以下是对该类的简要分析： 
 * ### 类名和包 
 * - **包名**：`com.cyberpeace.oj.course.controller.req` 
 * - **类名**：`ClassroomSignInReq` 
 * ### 注解 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModel` 注解，表示这是一个模型类。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，描述模型属性。 
 * ### 属性 
 * - `resourceId`：一个字符串类型的属性，表示课堂编号。 
 * - 使用了 `@NotBlank` 注解，确保该字段不为空或空白字符串。 
 * - 使用了 `@ApiModelProperty` 注解，描述该字段为“课堂编号”。 
 * ### 实现接口 
 * - 实现了 `Serializable` 接口，表明该类的对象可以序列化。 
 * ### 总结 
 * - `ClassroomSignInReq` 是一个用于课堂签到请求的数据传输对象。 
 * - 它包含一个必填的字符串属性 `resourceId`，用于标识课堂编号。 
 * - 通过 Lombok 注解简化了 getter 和 setter 方法的编写。 
 * - 通过 Swagger 注解提供了 API 文档生成所需的信息。 
 * 这个类主要用于接收客户端提交的课堂签到请求，并验证其中的必要参数。 
**/
@Getter
@Setter
@ApiModel
public class ClassroomSignInReq implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("课堂编号")
	@NotBlank
	private String resourceId;

}

