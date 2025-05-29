package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Web课程结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `WebCourseRes` 的 Java 类，该类实现了 `Serializable` 接口。这个类主要用于表示与 Web 课程相关的数据结构，并且使用了 Lombok 库来自动生成 getter 和 setter 方法。以下是对该类的详细分析： 
 * ### 包声明 
 * ```java 
 * package com.cyberpeace.oj.course.controller.res; 
 * ``` 
 * 这行代码指定了该类所在的包名。 
 * ### 导入语句 
 * ```java 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * import java.io.Serializable; 
 * ``` 
 * - `io.swagger.annotations.ApiModelProperty`: 用于在 Swagger 文档中描述属性。 
 * - `lombok.Getter` 和 `lombok.Setter`: Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * - `java.io.Serializable`: 标识该类可以序列化。 
 * ### 类声明 
 * ```java 
 * @Getter 
 * @Setter 
 * public class WebCourseRes implements Serializable { 
 * ``` 
 * - `@Getter` 和 `@Setter`: 自动生成所有字段的 getter 和 setter 方法。 
 * - `implements Serializable`: 表示该类可以被序列化。 
 * ### 字段说明 
 * 以下是该类中定义的所有字段及其对应的注释和 Swagger 注解： 
 * 1. **resourceId**: 资源编号，类型为 `String`。 
 * 2. **name**: 课程名称，类型为 `String`。 
 * 3. **description**: 课程简介，类型为 `String`。 
 * 4. **categoryId**: 课程方向 ID，类型为 `String`。 
 * 5. **logo**: 课程海报 URL，类型为 `String`。 
 * 6. **outline**: 课程大纲，类型为 `String`。 
 * 7. **difficulty**: 课程难度，类型为 `Integer`。 
 * 8. **lecturer**: 课程讲师，类型为 `String`。 
 * 9. **score**: 课程学分，类型为 `Integer`。 
 * 10. **directionName**: 课程方向名称，类型为 `String`。 
 * 11. **lessonCount**: 课程课时数量，类型为 `Integer`。 
 * 12. **isCollection**: 是否收藏，类型为 `Boolean`。 
 * 13. **heat**: 课程访问量，类型为 `Integer`。 
 * ### 总结 
 * `WebCourseRes` 类是一个数据传输对象（DTO），用于表示 Web 课程的相关信息。它包含多个属性，如课程名称、简介、难度等，并且这些属性都通过 Swagger 注解进行了文档描述。Lombok 注解使得不需要手动编写 getter 和 setter 方法，提高了代码的简洁性和可维护性。此外，该类实现了 `Serializable` 接口，可以在需要时进行序列化操作。 
**/
@Getter
@Setter
public class WebCourseRes implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty("课程简介")
	private String description;

	/**
	 * 类别id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程方向")
	private String categoryId;

	/**
	 * 图标
	 *
	 * @see String
	 */
	@ApiModelProperty("课程海报")
	private String logo;

	/**
	 * 大纲
	 *
	 * @see String
	 */
	@ApiModelProperty("课程大纲")
	private String outline;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程难度")
	private Integer difficulty;

	/**
	 * 讲师
	 *
	 * @see String
	 */
	@ApiModelProperty("课程讲师")
	private String lecturer;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程学分")
	private Integer score;

	/**
	 * 方向名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程方向名称")
	private String directionName;

	/**
	 * 课计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程课时数量")
	private Integer lessonCount;

	/**
	 * is 集合
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	/**
	 * 热
	 *
	 * @see Integer
	 */
	@ApiModelProperty("访问量")
	private Integer heat;

}

