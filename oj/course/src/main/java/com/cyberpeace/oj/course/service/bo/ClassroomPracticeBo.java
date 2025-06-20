package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import com.cyberpeace.common.model.ListRsp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 课堂练习 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ClassroomPracticeBo` 的 Java 类，用于表示课堂练习的业务对象（BO）。以下是对该类的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.service.bo` 
 * - **继承**：实现了 `Serializable` 接口，使得该对象可以被序列化。 
 * ### 注解 
 * - **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：自动生成所有字段的 getter 和 setter 方法。 
 * - `@AllArgsConstructor`：生成包含所有字段的构造函数。 
 * - `@NoArgsConstructor`：生成无参构造函数。 
 * - `@Builder`：生成一个 builder 模式的方法来创建对象。 
 * - **Swagger 注解**： 
 * - `@ApiModel`：标记这个类为 Swagger 文档中的模型。 
 * - `@ApiModelProperty`：用于描述类中的属性。 
 * ### 字段 
 * 1. **released** 
 * - **类型**：`Boolean` 
 * - **默认值**：`true` 
 * - **描述**：是否已发布。 
 * 2. **hasPractice** 
 * - **类型**：`Boolean` 
 * - **默认值**：`true` 
 * - **描述**：是否有习题。 
 * 3. **data** 
 * - **类型**：`ListRsp<PracticePaperEvaluateBo>` 
 * - **描述**：练习完成统计信息。 
 * ### 总结 
 * - **用途**：`ClassroomPracticeBo` 类主要用于封装课堂练习的相关信息，包括是否已发布、是否有习题以及练习完成统计等。 
 * - **特性**：通过 Lombok 注解减少了样板代码，提高了可读性和简洁性。使用 Swagger 注解便于生成 API 文档。 
 * - **依赖**：依赖于 `ListRsp` 和 `PracticePaperEvaluateBo` 类或接口。 
 * 这段代码是一个典型的用于数据传输的对象（DTO），用于在不同层之间传递课堂练习的信息。 
**/
@Getter
@Setter
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomPracticeBo implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 释放
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否已发布")
	private Boolean released=true;

	/**
	 * 有实践
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否有习题")
	private Boolean hasPractice=true;

	/**
	 * 数据
	 *
	 * @see ListRsp<PracticePaperEvaluateBo>
	 */
	@ApiModelProperty("练习完成统计")
	private ListRsp<PracticePaperEvaluateBo> data;
}

