/**
 * 这段代码定义了一个名为 `AdminCourseStatisticsRes` 的 Java 类，该类用于表示管理员课程统计结果。以下是详细的分析： 
 * ### 包声明和导入 
 * ```java 
 * package com.cyberpeace.oj.course.controller.res; 
 * import io.swagger.annotations.ApiModel; 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * import java.io.Serializable; 
 * ``` 
 * - `package com.cyberpeace.oj.course.controller.res;`：声明了类所在的包。 
 * - 导入了一些必要的类： 
 * - `io.swagger.annotations.ApiModel` 和 `io.swagger.annotations.ApiModelProperty`：用于 Swagger 文档注解。 
 * - `lombok.Getter` 和 `lombok.Setter`：用于自动生成 getter 和 setter 方法。 
 * - `java.io.Serializable`：表示该类可以序列化。 
 * ### 类定义 
 * ```java 
 * @ApiModel 
 * @Getter 
 * @Setter 
 * public class AdminCourseStatisticsRes implements Serializable { 
 * ``` 
 * - `@ApiModel`：用于描述这个类在 Swagger 中的模型信息。 
 * - `@Getter` 和 `@Setter`：使用 Lombok 自动生成 getter 和 setter 方法。 
 * - `implements Serializable`：表明该类可以被序列化。 
 * ### 成员变量 
 * ```java 
 * private String resourceId; 
 * private String name; 
 * private String categoryName; 
 * private Integer difficulty; 
 * private Integer lessonCount; 
 * private Integer heat; 
 * private String finishReportCount; 
 * private String createUser; 
 * ``` 
 * - 这些成员变量分别代表课程资源编号、课程名称、课程分类、课程难度、课时数、学习人数、提交报告数和创建者。 
 * - 每个成员变量都有对应的 `@ApiModelProperty` 注解，用于描述其在 Swagger 文档中的属性名。 
 * ### 总结 
 * - `AdminCourseStatisticsRes` 类是一个数据传输对象（DTO），用于封装课程统计信息。 
 * - 通过 Lombok 的 `@Getter` 和 `@Setter` 注解，简化了 getter 和 setter 方法的编写。 
 * - 使用 Swagger 注解 (`@ApiModel` 和 `@ApiModelProperty`)，方便生成 API 文档。 
 * - 实现 `Serializable` 接口，使得该对象可以进行序列化操作。 
 * 该类主要用于后台管理系统中展示课程统计信息，并且支持 API 文档的自动生成。 
**/
package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 管理课程统计学结果
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
public class AdminCourseStatisticsRes implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程资源编号")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程名称")
	private String name;

	/**
	 * 类别名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程分类")
	private String categoryName;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程难度")
	private Integer difficulty;

	/**
	 * 课计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时数")
	private Integer lessonCount;

	/**
	 * 热
	 *
	 * @see Integer
	 */
	@ApiModelProperty("学习人数")
	private Integer heat;

	/**
	 * 完成报告计数
	 *
	 * @see String
	 */
	@ApiModelProperty("提交报告数")
	private String finishReportCount;

	/**
	 * 创建用户
	 *
	 * @see String
	 */
	@ApiModelProperty("创建者")
	private String createUser;
}
