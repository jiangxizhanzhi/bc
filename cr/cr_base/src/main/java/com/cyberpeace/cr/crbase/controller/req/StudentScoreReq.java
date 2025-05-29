package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 学员得分请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `StudentScoreReq` 的 Java 类，位于包 `com.cyberpeace.cr.crbase.controller.req` 中。以下是代码的主要分析和总结： 
 * ### 功能描述： 
 * 该类是一个用于接收学员得分相关信息的请求体类，主要用于前端向后端传递数据。 
 * --- 
 * ### 主要特性： 
 * 1. **注解使用**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段添加了描述信息，便于生成接口文档。 
 * 2. **字段说明**： 
 * - `abilityPercentage`：能力占比，类型为字符串。 
 * - `abilityScore`：能力分数，类型为字符串。 
 * - `comment`：评语，类型为字符串。 
 * - `finalScore`：最终得分，类型为整数（`Integer`）。 
 * - `trainingPercentage`：训练占比，类型为字符串。 
 * - `roomId`：房间 ID，类型为字符串。 
 * - `user`：用户 ID，类型为字符串。 
 * 3. **设计目的**： 
 * - 该类作为一个请求体类，用于封装前端传递的学员得分相关数据，便于后端处理。 
 * --- 
 * ### 总结： 
 * - 这是一个简单的 POJO（Plain Old Java Object）类，用于封装请求数据。 
 * - 使用了 Lombok 和 Swagger 注解来减少冗余代码并增强可读性。 
 * - 各字段通过注释清晰地描述了其用途，适合用于 API 接口开发。 
**/
@Getter
@Setter
public class StudentScoreReq {

	/**
	 * 能力百分比
	 */
	@ApiModelProperty(value = "能力占比")
	private String abilityPercentage;

	/**
	 * 能力分数
	 */
	@ApiModelProperty(value = "能力分")
	private String abilityScore;

	/**
	 * 评论
	 */
	@ApiModelProperty(value = "评语")
	private String comment;

	/**
	 * 最终比分
	 */
	@ApiModelProperty(value = "最终得分")
	private Integer finalScore;

	/**
	 * 训练百分比
	 */
	@ApiModelProperty(value = "训练占比")
	private String trainingPercentage;

	/**
	 * 房间id
	 */
	@ApiModelProperty(value = "房间id")
	private String roomId;

	/**
	 * 用户
	 */
	@ApiModelProperty(value = "用户id")
	private String user;
}

