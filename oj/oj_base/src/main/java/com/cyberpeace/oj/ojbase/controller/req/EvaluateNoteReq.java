/**
 * 这段代码定义了一个名为 `EvaluateNoteReq` 的 Java 类，用于表示评估笔记请求体。以下是对其主要特性和功能的简要分析： 
 * ### 类定义与继承 
 * - **类名**：`EvaluateNoteReq` 
 * - **包**：`com.cyberpeace.oj.ojbase.controller.req` 
 * - **实现接口**：`Serializable`，意味着该对象可以被序列化，便于在网络上传输或持久化存储。 
 * ### 注解使用 
 * - **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：自动生成所有属性的 getter 和 setter 方法。 
 * - **Swagger 注解**： 
 * - `@ApiModel`：描述这个类作为一个模型（如 API 响应体）。 
 * - `@ApiModelProperty`：为类中的每个字段提供元数据，描述字段的作用和用途。 
 * ### 属性 
 * - `noteId`：笔记编号，必须是非空字符串（由 `@NotBlank` 注解保证）。 
 * - `evaluateComment`：评估评论，也必须是非空字符串。 
 * - `passed`：一个布尔值，表示是否通过评估，必须为非空（由 `@NotNull` 注解保证）。 
 * - `score`：评估分数，必须为非空（由 `@NotNull` 注解保证），类型为 `Double`。 
 * ### 功能描述 
 * `EvaluateNoteReq` 类主要用于接收评估笔记请求的数据。它包含了评估所需的基本信息，如笔记编号、评估评论、是否通过以及具体的评分。这些信息对于后端处理评估逻辑至关重要。 
 * ### 总结 
 * 该类是一个典型的请求体类，用于封装前端传来的评估请求数据。通过 Lombok 注解简化了代码，使得类更加简洁且易于维护。Swagger 注解则帮助文档生成工具更好地理解并展示这个类的结构和用途。 
**/
package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 评估注意请求体
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
public class EvaluateNoteReq implements Serializable {

	/**
	 * 注意id
	 *
	 * @see String
	 */
	@ApiModelProperty("笔记编号")
	@NotBlank
	private String noteId;

	/**
	 * 评估评论
	 *
	 * @see String
	 */
	@ApiModelProperty("评语")
	@NotBlank
	private String evaluateComment;

	/**
	 * 通过
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否通过")
	@NotNull
	private Boolean passed;

	/**
	 * 得分
	 *
	 * @see Double
	 */
	@ApiModelProperty("得分")
	@NotNull
	private Double score;

}
