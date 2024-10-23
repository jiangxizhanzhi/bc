package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RaceStatistics {

	@ApiModelProperty(value = "计分排行规则")
	private Integer scoreRanking = 0;

	@ApiModelProperty(value = "轮次机制规则")
	private Integer roundMechanism = 0;

	@ApiModelProperty(value = "违规判定规则")
	private Integer violationJudge = 0;

}
