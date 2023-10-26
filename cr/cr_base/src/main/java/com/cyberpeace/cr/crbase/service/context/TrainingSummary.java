package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.cr.crbase.dao.entity.MissionBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练概况
 */
@Data
public class TrainingSummary {

	@ApiModelProperty(value = "客观得分")
	private Integer getScoreObjective = 0;

	@ApiModelProperty(value = "主观得分")
	private Integer getScoreSubjective = 0;

	@ApiModelProperty(value = "客观总分")
	private Integer sumScoreObjective = 0;

	@ApiModelProperty(value = "主观总分")
	private Integer sumScoreSubjective = 0;

	@ApiModelProperty(value = "训练用时")
	private String timeCost;

	@ApiModelProperty(value = "推荐用时")
	private Integer duration = 0;

	public void addScore(Integer addend, Integer missionType) {
		if (Boolean.TRUE.equals(MissionBase.isObjective(missionType))) {
			this.getScoreObjective += addend;
		} else {
			this.getScoreSubjective += addend;
		}
	}

	public void addTotal(Integer addend, Integer missionType) {
		if (Boolean.TRUE.equals(MissionBase.isObjective(missionType))) {
			this.sumScoreObjective += addend;
		} else {
			this.sumScoreSubjective += addend;
		}
	}

}
