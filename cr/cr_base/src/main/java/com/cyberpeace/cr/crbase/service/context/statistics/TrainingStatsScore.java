package com.cyberpeace.cr.crbase.service.context.statistics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TrainingStatsScore implements Serializable {

	@ApiModelProperty("单兵训练得分")
	private Integer singleScore;

	@ApiModelProperty("红队训练得分")
	private Integer redScore;

	@ApiModelProperty("蓝队训练得分")
	private Integer blueScore;

	@ApiModelProperty("红蓝对抗得分")
	private Integer rbScore;

	@ApiModelProperty("总分")
	private Integer score;

	public TrainingStatsScore() {
		this.singleScore = 0;
		this.redScore = 0;
		this.blueScore = 0;
		this.rbScore = 0;
		this.score = 0;
	}
}
