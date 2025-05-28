/**
 * 这段代码定义了一个名为 `ResourceAuthReq` 的 Java 类，用于表示资源授权访问的请求体。以下是对该类的简要分析： 
 * ### 包声明和导入 
 * ```java 
 * package com.cyberpeace.oj.ojbase.controller.req; 
 * import java.util.List; 
 * import io.swagger.annotations.ApiModel; 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * import javax.validation.constraints.NotEmpty; 
 * import javax.validation.constraints.NotNull; 
 * ``` 
 * - 这段代码导入了所需的包，包括 Lombok 注解、JSR 303 验证注解、以及 Swagger 注解。 
 * ### 类定义 
 * ```java 
 * @Getter 
 * @Setter 
 * @ApiModel("资源授权访问") 
 * public class ResourceAuthReq { 
 * ``` 
 * - `@Getter` 和 `@Setter` 是 Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * - `@ApiModel("资源授权访问")` 是 Swagger 注解，用于描述该模型。 
 * ### 成员变量 
 * ```java 
 * /** 
 * * 资源id列表 
 * * 
 * * @see List<String> 
 * */ 
 * @ApiModelProperty("资源ID") 
 * @NotEmpty 
 * private List<String> resourceIds; 
 * /** 
 * * 读认证id列表 
 * * 
 * * @see List<String> 
 * */ 
 * @ApiModelProperty("授权资源") 
 * private List<String> readAuthIds; 
 * /** 
 * * 读取身份验证类型 
 * * 
 * * @see Integer 
 * */ 
 * @ApiModelProperty("授权模式, 1-所有, 2-自定义") 
 * @NotNull 
 * private Integer readAuthType; 
 * ``` 
 * - `resourceIds`: 一个非空的字符串列表，表示资源 ID 列表。 
 * - `readAuthIds`: 一个字符串列表，表示读认证 ID 列表。 
 * - `readAuthType`: 一个整数，表示授权模式（1 表示所有，2 表示自定义），且不能为空。 
 * ### 总结 
 * - `ResourceAuthReq` 类用于处理资源授权访问的请求体。 
 * - 类中的成员变量通过注解进行了详细的描述和验证。 
 * - 使用 Lombok 简化了 getter 和 setter 的生成。 
 * - 通过 Swagger 注解，可以方便地生成 API 文档。 
 * 这段代码结构清晰，注解使用得当，便于后续的开发和维护。 
**/
package com.cyberpeace.oj.ojbase.controller.req;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 资源认证请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel("资源授权访问")
public class ResourceAuthReq {

	/**
	 * 资源id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("资源ID")
	@NotEmpty
	private List<String> resourceIds;

	/**
	 * 读认证id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("授权资源")
	private List<String> readAuthIds;

	/**
	 * 读取身份验证类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("授权模式, 1-所有, 2-自定义")
	@NotNull
	private Integer readAuthType;

}
