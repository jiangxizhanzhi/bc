package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 学员得分请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class StudentScoreReq {

	/**
	 * 能力百分比
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "能力占比")
	private String abilityPercentage;

	/**
	 * 能力分数
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "能力分")
	private String abilityScore;

	/**
	 * 评论
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "评语")
	private String comment;

	/**
	 * 最终比分
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "最终得分")
	private Integer finalScore;

	/**
	 * 训练百分比
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "训练占比")
	private String trainingPercentage;

	/**
	 * 房间id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "房间id")
	private String roomId;

	/**
	 * 用户
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "用户id")
	private String user;
}
