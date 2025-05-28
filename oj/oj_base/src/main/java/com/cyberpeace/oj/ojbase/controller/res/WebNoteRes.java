/**
 * 这段代码定义了一个名为 `WebNoteRes` 的 Java 类，用于表示与 Web 网页相关的笔记结果。以下是对该类的简要分析： 
 * ### 类名与包声明 
 * - **包声明**: `com.cyberpeace.oj.ojbase.controller.res` 
 * - 表示这个类位于 `com.cyberpeace.oj.ojbase.controller.res` 包下。 
 * - **类名**: `WebNoteRes` 
 * - 这个类的主要目的是封装与 Web 网页相关的笔记信息。 
 * ### 注解 
 * - **Lombok 注解** 
 * - `@Getter`, `@Setter`: 自动生成所有属性的 getter 和 setter 方法。 
 * - `@Builder`: 自动生成一个 Builder 模式构造器。 
 * - `@AllArgsConstructor`: 自动生成包含所有参数的构造方法。 
 * - `@NoArgsConstructor`: 自动生成无参构造方法。 
 * - **Swagger 注解** 
 * - `@ApiModelProperty`: 用于 Swagger 文档生成，为每个字段提供描述信息。 
 * ### 属性 
 * - `resourceId`: 笔记编号（类型为 `String`）。 
 * - `content`: 笔记内容（类型为 `String`）。 
 * - `createTime`: 笔记创建时间（类型为 `Date`）。 
 * - `updateTime`: 更新时间（类型为 `Date`）。 
 * - `score`: 分值（类型为 `Double`）。 
 * - `passed`: 是否通过（类型为 `Boolean`）。 
 * - `evaluateComment`: 评估内容（类型为 `String`）。 
 * - `isSubmit`: 实验报告是否已提交（类型为 `Boolean`）。 
 * ### 继承与实现 
 * - `implements Serializable`: 表明这个类实现了 `Serializable` 接口，可以被序列化。 
 * ### 总结 
 * - **用途**: `WebNoteRes` 类主要用于封装 Web 网页中笔记的相关信息，便于在不同层之间传递数据。 
 * - **设计**: 使用了 Lombok 注解来简化代码，减少了样板代码，提高了开发效率。 
 * - **功能**: 提供了笔记的详细信息，包括笔记的基本信息、评分、状态等。 
 * 总体来说，这是一个简洁且功能明确的数据传输对象（DTO），适用于前后端交互和数据传输。 
**/
package com.cyberpeace.oj.ojbase.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Web注意结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebNoteRes implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("笔记编号")
	private String resourceId;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty("笔记内容")
	private String content;

	/**
	 * 创建时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("笔记创建时间")
	private Date createTime;

	/**
	 * 更新时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("更新时间")
	private Date updateTime;

	/**
	 * 得分
	 *
	 * @see Double
	 */
	@ApiModelProperty("分值")
	private Double score;

	/**
	 * 通过
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否通过")
	private Boolean passed;

	/**
	 * 评估评论
	 *
	 * @see String
	 */
	@ApiModelProperty("评估内容")
	private String evaluateComment;

	/**
	 * 是提交
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("实验报告是否已提交")
	private Boolean isSubmit;

}
