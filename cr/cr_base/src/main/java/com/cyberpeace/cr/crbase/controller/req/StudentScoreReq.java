package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 学员得分请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
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
