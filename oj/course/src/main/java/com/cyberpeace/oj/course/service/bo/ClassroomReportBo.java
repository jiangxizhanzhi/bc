package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 课堂举报 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ClassroomReportBo` 的 Java 类，该类实现了 `Serializable` 接口。这个类主要用于表示课堂举报的信息，并且使用了 Lombok 注解来简化代码。 
 * ### 主要特性： 
 * 1. **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：自动生成所有字段的 getter 和 setter 方法。 
 * 2. **Swagger 注解**： 
 * - `@ApiModel`：用于描述整个类的模型信息。 
 * - `@ApiModelProperty`：用于描述类中的各个字段，提供额外的文档说明。 
 * 3. **字段**： 
 * - `userId`, `userName`, `name`: 用户相关信息。 
 * - `organizationId`, `organizationName`: 组织相关信息。 
 * - `noteId`: 笔记编号。 
 * - `content`: 实验报告内容。 
 * - `score`: 实验报告得分。 
 * - `evaluatorId`, `evaluatorUserName`, `evaluatorName`: 评分人的相关信息。 
 * - `evaluateTime`: 评分时间。 
 * - `submitTime`: 报告提交时间。 
 * - `evaluateComment`: 评语。 
 * - `passed`: 是否通过。 
 * ### 总结： 
 * - `ClassroomReportBo` 类是一个数据传输对象（DTO），用于在不同层之间传递课堂举报的相关信息。 
 * - 使用 Lombok 减少了样板代码，提高了开发效率。 
 * - Swagger 注解有助于生成 API 文档，便于接口的理解和维护。 
 * 这段代码设计清晰，功能明确，适合用于课堂举报相关的业务场景。 
**/
@Getter
@Setter
@ApiModel
public class ClassroomReportBo implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty("用户名")
	private String userName;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("用户姓名")
	private String name;

	/**
	 * 组织id
	 *
	 * @see String
	 */
	@ApiModelProperty("组织编号")
	private String organizationId;

	/**
	 * 组织名称
	 *
	 * @see String
	 */
	@ApiModelProperty("组织名称")
	private String organizationName;

	/**
	 * 注意id
	 *
	 * @see String
	 */
	@ApiModelProperty("笔记编号")
	private String noteId;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty("实验报告内容")
	private String content;

	/**
	 * 得分
	 *
	 * @see Double
	 */
	@ApiModelProperty("实验报告得分")
	private Double score;

	/**
	 * 计算器id
	 *
	 * @see String
	 */
	@ApiModelProperty("评分人编号")
	private String evaluatorId;

	/**
	 * 计算器用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty("评分人名称")
	private String evaluatorUserName;

	/**
	 * 计算器名称
	 *
	 * @see String
	 */
	@ApiModelProperty("评分人姓名")
	private String evaluatorName;

	/**
	 * 评估时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("评分时间")
	private Date evaluateTime;

	/**
	 * 提交时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("报告提交时间")
	private Date submitTime;

	/**
	 * 评估评论
	 *
	 * @see String
	 */
	@ApiModelProperty("评语")
	private String evaluateComment;

	/**
	 * 通过
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否通过")
	private Boolean passed;

}

