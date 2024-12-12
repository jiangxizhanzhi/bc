package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.service.context.TrainingSummary;
import com.cyberpeace.cr.crbase.service.context.score.TaskStatistics;
import com.cyberpeace.cr.crbase.service.context.score.TrainingScore;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 学员得分响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class StudentScoreRsp {

	/**
	 * 能力百分比
	 */
	@ApiModelProperty(value = "能力占比")
	private String abilityPercentage = "";

	/**
	 * 能力分数
	 */
	@ApiModelProperty(value = "能力分")
	private List<Integer> abilityScore = CrCommonUtil
			.getZeroList(CrCommonConstants.SCORE_ABILITY_SIZE);

	/**
	 * 能力分数结果
	 */
	@ApiModelProperty(value = "能力分值")
	private Integer abilityScoreResult = 0;

	/**
	 * 评论
	 */
	@ApiModelProperty(value = "评语")
	private String comment = "";

	/**
	 * 最终比分
	 */
	@ApiModelProperty(value = "最终分数")
	private Integer finalScore = 0;

	/**
	 * 训练百分比
	 */
	@ApiModelProperty(value = "训练占比")
	private String trainingPercentage = "";

	/**
	 * 训练总结
	 */
	@ApiModelProperty(value = "训练分数")
	private TrainingSummary trainingSummary;

	/**
	 * 任务统计
	 */
	@ApiModelProperty(value = "任务统计")
	private TaskStatistics taskStatistics;

	/**
	 * 训练得分
	 */
	@ApiModelProperty(value = "阶段统计")
	private TrainingScore trainingScore;

	public void setAbilityScore(List<Integer> scoreList) {
		this.abilityScore = scoreList;
		int result = 0;
		for (Integer integer : scoreList) {
			result += integer * CrCommonConstants.SCORE_PER_STAR;
		}
		this.abilityScoreResult = result;
	}

}