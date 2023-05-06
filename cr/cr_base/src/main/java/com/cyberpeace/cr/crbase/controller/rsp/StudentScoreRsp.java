package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.ArrayList;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.TrainingScore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 学生分数返回值
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
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
	private TrainingScore trainingScore = new TrainingScore();

}