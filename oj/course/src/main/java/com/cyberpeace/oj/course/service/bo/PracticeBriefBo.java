package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 实践简报 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `PracticeBriefBo` 的 Java 类，用于表示实践简报的基本信息。以下是对其主要特性和功能的简要分析： 
 * ### 类概述 
 * - **包声明**：该类位于 `com.cyberpeace.oj.course.service.bo` 包中。 
 * - **导入语句**： 
 * - `io.swagger.annotations.ApiModel` 和 `io.swagger.annotations.ApiModelProperty` 用于生成 API 文档注解。 
 * - `lombok.Getter` 和 `lombok.Setter` 注解简化了属性的 getter 和 setter 方法的编写。 
 * - `java.io.Serializable` 接口表示该类可以序列化，通常用于在网络上传输对象或持久化到磁盘。 
 * ### 类特性 
 * - **继承与实现**：`PracticeBriefBo` 类实现了 `Serializable` 接口。 
 * - **Lombok 注解**：使用了 `@Getter` 和 `@Setter` 注解来自动生成属性的访问器方法，从而减少了样板代码。 
 * - **Swagger 注解**：使用了 `@ApiModel` 和 `@ApiModelProperty` 注解来描述这个类及其属性，便于在 API 文档中展示这些信息。 
 * ### 属性 
 * - **accuracy**：表示理论题的正确率，类型为 `String`。 
 * - **level**：表示评分等级，类型为 `Integer`，默认值为 `null`。 
 * - **objectCount**：表示客观题的数量，类型为 `Integer`。 
 * - **subjectCount**：表示主观题的数量，类型为 `Integer`。 
 * - **totalCount**：表示题目总数量，类型为 `Integer`。 
 * ### 功能 
 * - 该类主要用于存储和传输实践简报的信息，包括题目数量统计和评分等级等数据。 
 * - 通过 Lombok 注解简化了属性的访问器方法，提高了代码的可读性和维护性。 
 * - 使用 Swagger 注解使得这个类在 API 文档中更容易被理解和使用。 
 * ### 总结 
 * `PracticeBriefBo` 类是一个简单的数据传输对象（DTO），用于存储和传输实践简报的相关信息。通过使用 Lombok 和 Swagger 注解，它有效地减少了代码量并增强了文档的可读性。 
**/
@Getter
@Setter
@ApiModel
public class PracticeBriefBo implements Serializable {

	/**
	 * 准确性
	 *
	 * @see String
	 */
	@ApiModelProperty("理论题正确率")
	private String accuracy;

	/**
	 * 水平
	 *
	 * @see Integer
	 */
	@ApiModelProperty("评分等级")
	private Integer level=null;

	/**
	 * 对象计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("客观题数量")
	private Integer objectCount;

	/**
	 * 主题计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("主观题数量")
	private Integer subjectCount;

	/**
	 * 总计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("题目总数量")
	private Integer totalCount;

}

