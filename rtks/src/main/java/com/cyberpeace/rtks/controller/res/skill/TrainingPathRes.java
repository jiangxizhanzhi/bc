package com.cyberpeace.rtks.controller.res.skill;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.rtks.service.bo.skill.TrainingBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TrainingPathRes implements Serializable {

	@ApiModelProperty(value = "训练路径")
	private List<TrainingBo> path;

	@ApiModelProperty(value = "全部训练")
	private List<RelatedTrainingRes> related;
}
