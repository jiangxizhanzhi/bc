/**
 * 这段代码定义了一个名为 `GroupSceneReq` 的 Java 类，用于表示群场景请求体。以下是对该代码的简要分析： 
 * ### 包声明 
 * - `package com.cyberpeace.oj.course.controller.req;` 
 * - 这行代码声明了该类所在的包是 `com.cyberpeace.oj.course.controller.req`。 
 * ### 导入的类 
 * - `io.swagger.annotations.ApiModel;` 
 * - `io.swagger.annotations.ApiModelProperty;` 
 * - `lombok.Getter;` 
 * - `lombok.Setter;` 
 * - `javax.validation.constraints.NotBlank;` 
 * - `java.io.Serializable;` 
 * 这些导入的类主要用于： 
 * - 使用 Swagger 注解来描述 API 模型。 
 * - 使用 Lombok 注解来简化 getter 和 setter 方法的生成。 
 * - 使用 JSR 303 Bean Validation 来确保字段不为空。 
 * - 实现 `Serializable` 接口以便对象可以序列化。 
 * ### 类定义 
 * - `@Getter` 
 * - `@Setter` 
 * - `@ApiModel` 
 * - 这些注解分别由 Lombok 和 Swagger 提供，用于自动生成 getter 和 setter 方法，并描述该类为一个 API 模型。 
 * ### 类成员变量 
 * - `private String groupId;` 
 * - 定义了一个私有字符串类型的成员变量 `groupId`，表示群组 ID。 
 * ### 字段注解 
 * - `@ApiModelProperty("课堂组编号")` 
 * - 用 Swagger 的 `ApiModelProperty` 注解来描述 `groupId` 字段的用途。 
 * - `@NotBlank` 
 * - 用 JSR 303 的 `NotBlank` 注解来确保 `groupId` 字段不能为空。 
 * ### 总结 
 * - `GroupSceneReq` 类是一个用于表示群场景请求体的数据传输对象（DTO）。 
 * - 该类包含一个必需的字符串字段 `groupId`，并使用 Swagger 和 Lombok 注解来简化开发过程和描述 API 模型。 
 * - 该类实现了 `Serializable` 接口，允许对象被序列化。 
 * 这个类的主要目的是在 API 请求中传递群组信息，并确保群组 ID 是必需且有效的。 
**/
package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 群场景请求体
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
public class GroupSceneReq implements Serializable {

	/**
	 * 群id
	 *
	 * @see String
	 */
	@ApiModelProperty("课堂组编号")
	@NotBlank
	private String groupId;

}
