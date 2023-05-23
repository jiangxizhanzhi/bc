package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.ArrayList;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.TrainingSummary;
import com.cyberpeace.cr.crbase.service.context.score.TaskStatistics;
import com.cyberpeace.cr.crbase.service.context.score.TrainingScore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StudentScoreRsp {

	@ApiModelProperty(value = "能力占比")
	private String abilityPercentage = "";

	@ApiModelProperty(value = "能力分")
	private List<Integer> abilityScore = new ArrayList<>();

	@ApiModelProperty(value = "评语")
	private String comment = "";

	@ApiModelProperty(value = "最终分数")
	private Integer finalScore = 0;

	@ApiModelProperty(value = "训练占比")
	private String trainingPercentage = "";

	@ApiModelProperty(value = "训练分数")
	private TrainingSummary trainingSummary;

	@ApiModelProperty(value = "任务统计")
	private TaskStatistics taskStatistics;

	@ApiModelProperty(value = "阶段统计")
	private TrainingScore trainingScore;

}