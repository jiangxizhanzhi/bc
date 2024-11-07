package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.cr.crbase.dao.entity.Mission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练概况
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class TrainingSummary {

	/**
	 * 获取得分目
	 */
	@ApiModelProperty(value = "客观得分")
	private Integer getScoreObjective = 0;

	/**
	 * 获取得分主观
	 */
	@ApiModelProperty(value = "主观得分")
	private Integer getScoreSubjective = 0;

	/**
	 * SUM SCORE 目标
	 */
	@ApiModelProperty(value = "客观总分")
	private Integer sumScoreObjective = 0;

	/**
	 * 总和分数 主观
	 */
	@ApiModelProperty(value = "主观总分")
	private Integer sumScoreSubjective = 0;

	/**
	 * 时间成本
	 */
	@ApiModelProperty(value = "训练用时")
	private String timeCost;

	/**
	 * 期间
	 */
	@ApiModelProperty(value = "推荐用时")
	private Integer duration = 0;

	public void addScore(Integer addend, Integer missionType) {
		if (Mission.isObjective(missionType)) {
			this.getScoreObjective += addend;
		} else {
			this.getScoreSubjective += addend;
		}
	}

	public void addTotal(Integer addend, Integer missionType) {
		if (Mission.isObjective(missionType)) {
			this.sumScoreObjective += addend;
		} else {
			this.sumScoreSubjective += addend;
		}
	}

}
