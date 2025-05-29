package com.cyberpeace.oj.course.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课程列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CourseListReq` 的 Java 类，该类继承自 `PageReqBase`。这个类主要用于处理课程列表的请求参数，并且使用了 Lombok 库来简化 getter 和 setter 方法的生成。 
 * ### 主要特性与功能 
 * 1. **继承自 `PageReqBase`**：这表明 `CourseListReq` 类可能用于分页查询课程列表。 
 * 2. **注解**： 
 * - `@ApiModel`：表示这个类是一个模型类，通常用于 API 文档生成工具（如 Swagger）中描述模型。 
 * - `@ApiModelProperty`：用于给模型中的字段添加描述信息，有助于 API 文档生成工具更好地展示这些字段的信息。 
 * 3. **属性**： 
 * - `eBook`：一个整数类型的变量，用来标识课程类型（基础、攻击、防御等）。 
 * - `direction`：一个字符串类型的变量，表示课程的方向。 
 * - `difficulty`：一个整数类型的变量，表示课程的难度等级。 
 * - `role`：一个整数类型的变量，表示课程相关的岗位。 
 * ### 总结 
 * - **用途**：`CourseListReq` 是一个用于处理课程列表查询请求的实体类，支持通过类型、方向、难度和岗位等条件进行筛选。 
 * - **技术点**：使用了 Lombok 来简化代码，提高了可读性和开发效率。同时利用 Swagger 注解增强了 API 文档的描述能力。 
 * - **继承关系**：通过继承 `PageReqBase` 类，实现了分页查询的功能。 
 * 这个类的设计简洁明了，符合现代 Java 开发的最佳实践。 
**/
@ApiModel
@Getter
@Setter
public class CourseListReq extends PageReqBase {

	/**
	 * 电子书
	 *
	 * @see Integer
	 */
	@ApiModelProperty("类型 基础=0，攻击=2，防御=3")
	private Integer eBook;

	/**
	 * 方向
	 *
	 * @see String
	 */
	@ApiModelProperty("方向")
	private String direction;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("难度")
	private Integer difficulty;
	/**
	 * 岗位
	 *
	 * @see Integer
	 */
	@ApiModelProperty("岗位")
	private Integer role;

}

