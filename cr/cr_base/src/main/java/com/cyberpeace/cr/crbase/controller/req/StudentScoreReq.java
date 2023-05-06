package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 学生分数请求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class StudentScoreReq {

	@ApiModelProperty(value = "能力占比")
	private String abilityPercentage;

	@ApiModelProperty(value = "能力分")
	private String abilityScore;

	@ApiModelProperty(value = "评语")
	private String comment;

	@ApiModelProperty(value = "最终得分")
	private Integer finalScore;

	@ApiModelProperty(value = "训练占比")
	private String trainingPercentage;

	@ApiModelProperty(value = "房间id")
	private String roomId;

	@ApiModelProperty(value = "用户id")
	private String user;
}
