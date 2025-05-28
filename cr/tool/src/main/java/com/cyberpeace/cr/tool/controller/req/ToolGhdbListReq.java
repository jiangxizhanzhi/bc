/**
 * 这段代码定义了一个名为 `ToolGhdbListReq` 的 Java 类，该类用于处理工具 GHDB 数据库列表请求的数据。以下是对该代码的简要分析： 
 * ### 包声明 
 * ```java 
 * package com.cyberpeace.cr.tool.controller.req; 
 * ``` 
 * - 这行代码声明了该类所在的包。 
 * ### 导入语句 
 * ```java 
 * import com.cyberpeace.common.model.PageReqBase; 
 * import io.swagger.annotations.ApiModel; 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * ``` 
 * - 导入了所需的外部类和注解： 
 * - `PageReqBase` 是一个基类，可能用于处理分页请求。 
 * - `ApiModel` 和 `ApiModelProperty` 是 Swagger 注解，用于生成 API 文档。 
 * - `Getter` 和 `Setter` 是 Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * ### 类声明 
 * ```java 
 * @ApiModel 
 * public class ToolGhdbListReq extends PageReqBase { 
 * ``` 
 * - `ToolGhdbListReq` 类继承自 `PageReqBase` 类，并使用了 `@ApiModel` 注解，表示这是一个 API 模型类。 
 * ### 成员变量 
 * ```java 
 * @ApiModelProperty(value = "标题") 
 * @Getter 
 * @Setter 
 * private String title; 
 * @ApiModelProperty(value = "作者") 
 * @Getter 
 * @Setter 
 * private String author; 
 * ``` 
 * - `title` 和 `author` 是两个私有成员变量，分别表示标题和作者。 
 * - 使用了 `@ApiModelProperty` 注解来描述这些字段的作用。 
 * - `@Getter` 和 `@Setter` 注解由 Lombok 自动生成相应的 getter 和 setter 方法。 
 * ### 总结 
 * - `ToolGhdbListReq` 类主要用于处理工具 GHDB 数据库列表请求，包含标题和作者两个字段。 
 * - 通过继承 `PageReqBase` 类，该类可以支持分页功能。 
 * - 使用 Swagger 注解生成 API 文档，并通过 Lombok 减少了样板代码。 
 * 这个类的设计目的是为了方便前端发送查询请求时传递相关参数，并且可以通过 Swagger 自动生成 API 文档，方便接口的使用和维护。 
**/
package com.cyberpeace.cr.tool.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 工具GHDB 数据库列表请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@ApiModel
public class ToolGhdbListReq extends PageReqBase {

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题")
	@Getter
	@Setter
	private String title;

	/**
	 * 作者
	 */
	@ApiModelProperty(value = "作者")
	@Getter
	@Setter
	private String author;
}
