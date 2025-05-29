package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.practice.service.bo.TheoryLessonBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课理论博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see TheoryLessonBo
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LessonTheoryBo` 的类，该类继承自 `TheoryLessonBo`。以下是对这段代码的简要分析： 
 * ### 类定义 
 * - **类名**: `LessonTheoryBo` 
 * - **包**: `com.cyberpeace.oj.course.service.bo` 
 * - **继承**: `TheoryLessonBo` 
 * ### 注解 
 * - **@ApiModel**: 表示这是一个用于 Swagger 文档的模型类。 
 * - **@Getter** 和 **@Setter**: Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * ### 成员变量 
 * - **resolvedDetail**: 类型为 `PracticeSolvedBo`，表示解题详情。使用了 `@ApiModelProperty` 注解，说明这个字段会在 Swagger 文档中显示为 "解题详情"。 
 * ### 总结 
 * - **功能**: `LessonTheoryBo` 类扩展了 `TheoryLessonBo` 类，并添加了一个额外的成员变量 `resolvedDetail` 来存储解题详情。 
 * - **用途**: 这个类可能用于课程相关的理论部分，并且包含了解决某个问题的具体细节。 
 * - **技术栈**: 使用了 Lombok 注解来简化代码，以及 Swagger 注解来生成 API 文档。 
 * 这段代码的主要目的是扩展一个现有的理论课程模型，并增加一个新的字段来存储解题详情。 
**/
@Getter
@Setter
@ApiModel
public class LessonTheoryBo extends TheoryLessonBo {

	/**
	 * 解决详情
	 *
	 * @see PracticeSolvedBo
	 */
	@ApiModelProperty("解题详情")
	private PracticeSolvedBo resolvedDetail;

}

