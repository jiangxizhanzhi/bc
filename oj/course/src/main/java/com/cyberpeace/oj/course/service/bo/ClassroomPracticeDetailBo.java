package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 课堂实践详情博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ClassroomPracticeDetailBo` 的 Java 类，用于表示课堂实践详情。以下是对此代码的简要分析： 
 * ### 包和导入 
 * - **包声明**：`com.cyberpeace.oj.course.service.bo` 
 * - **导入**： 
 * - `java.io.Serializable`：该类实现了 `Serializable` 接口。 
 * - `java.util.Map`：使用了 `Map` 类型的成员变量。 
 * - `io.swagger.annotations.*`：用于生成 Swagger 文档注解。 
 * - `lombok.*`：Lombok 注解用于简化代码，如 `@Getter`, `@Setter`, `@Builder`, `@AllArgsConstructor`, `@NoArgsConstructor`。 
 * ### 类声明 
 * - **类名**：`ClassroomPracticeDetailBo` 
 * - **继承**：实现了 `Serializable` 接口。 
 * - **注解**： 
 * - `@ApiModel`：用于 Swagger 文档，描述这个类的用途。 
 * - `@Getter` 和 `@Setter`：自动为所有字段生成 getter 和 setter 方法。 
 * - `@Builder`：允许使用构建器模式创建对象。 
 * - `@AllArgsConstructor` 和 `@NoArgsConstructor`：分别生成包含所有字段的构造函数和无参构造函数。 
 * ### 字段 
 * - **lessonName**：课程名称，类型为 `String`。 
 * - **submitUserName**：提交用户的名称，类型为 `String`。 
 * - **accuracy**：准确性，类型为 `String`。 
 * - **practiceCount**：实践计数（题目数量），类型为 `Integer`。 
 * - **level**：水平评级，类型为 `Integer`。 
 * - **practiceList**：执业名单（课后练习题信息），类型为 `Map<String, LessonPracticeListBo>`。 
 * ### 总结 
 * - **目的**：该类主要用于存储和传输课堂实践相关的详细信息。 
 * - **特点**： 
 * - 使用 Lombok 注解减少了样板代码。 
 * - 通过 Swagger 注解便于生成 API 文档。 
 * - 字段包括课程名称、提交用户名称、准确性、题目数量、水平评级以及课后练习题信息。 
 * - **用途**：在系统中传递课堂实践相关数据，可能用于展示或处理课堂实践结果。 
 * 这段代码简洁且功能明确，适合在需要传递课堂实践详情的场景中使用。 
**/
@Getter
@Setter
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomPracticeDetailBo implements Serializable {

	/**
	 * 课名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课时名称")
	private String lessonName;

	/**
	 * 提交用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty("学员名字")
	private String submitUserName;

	/**
	 * 准确性
	 *
	 * @see String
	 */
	@ApiModelProperty("准确率")
	private String accuracy;

	/**
	 * 实践计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("题目数量")
	private Integer practiceCount;

	/**
	 * 水平
	 *
	 * @see Integer
	 */
	@ApiModelProperty("评级")
	private Integer level;

	/**
	 * 执业名单
	 *
	 * @see Map<String, LessonPracticeListBo>
	 */
	@ApiModelProperty("课后练习题信息")
	private Map<String, LessonPracticeListBo> practiceList;
}

