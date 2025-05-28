package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 比赛统计数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class RaceStatistics {

	@ApiModelProperty(value = "计分排行规则")
	private Integer scoreRanking = 0;

	@ApiModelProperty(value = "轮次机制规则")
	private Integer roundMechanism = 0;

	@ApiModelProperty(value = "违规判定规则")
	private Integer violationJudge = 0;

}
