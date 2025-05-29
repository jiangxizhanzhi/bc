package com.cyberpeace.oj.course.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课统计学列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LessonStatisticsListReq` 的 Java 类，该类用于表示获取课程统计信息列表的请求参数。以下是对该代码的简要分析： 
 * ### 包声明和导入 
 * - **包声明**：`com.cyberpeace.oj.course.controller.req` 表示该类位于 `oj` 项目下的 `course` 模块的 `controller.req` 子包中。 
 * - **导入**： 
 * - `com.cyberpeace.common.model.PageReqBase`：这是一个基类，可能包含了分页相关的属性和方法。 
 * - `io.swagger.annotations.ApiModel` 和 `io.swagger.annotations.ApiModelProperty`：这些注解用于生成 API 文档。 
 * - `lombok.Getter` 和 `lombok.Setter`：Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * ### 类声明 
 * - **类名**：`LessonStatisticsListReq` 
 * - **继承**：该类继承了 `PageReqBase` 类，可能包含了分页相关的方法和属性。 
 * - **注解**： 
 * - `@ApiModel`：描述该类是一个 API 模型，通常用于 Swagger 文档生成。 
 * - `@Getter` 和 `@Setter`：Lombok 注解，自动生成属性的 getter 和 setter 方法。 
 * ### 属性 
 * - **courseId**：字符串类型，表示课程 ID。 
 * - **search**：字符串类型，表示搜索关键字。 
 * - **type**：整数类型，表示课时类型。 
 * ### 总结 
 * - **用途**：该类用于封装从客户端到服务器端的请求参数，特别是用于获取课程统计信息列表的请求。 
 * - **功能**：通过继承 `PageReqBase` 类，该类可以处理分页相关的参数。同时，通过 Lombok 注解简化了代码量，减少了手动编写 getter 和 setter 方法的工作。 
 * - **文档**：通过 Swagger 注解，可以方便地生成 API 文档，便于前端开发人员了解接口使用方式。 
 * 这个类的设计简洁且实用，适用于典型的 RESTful API 开发场景。 
**/
@ApiModel
@Getter
@Setter
public class LessonStatisticsListReq extends PageReqBase {

	/**
	 * 课程id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程ID")
	private String courseId;

	/**
	 * 搜索
	 *
	 * @see String
	 */
	@ApiModelProperty("名称查询")
	private String search;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时类型")
	private Integer type;

}

