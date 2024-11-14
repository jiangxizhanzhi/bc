package com.cyberpeace.rtks.controller.res.skill;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.rtks.service.bo.skill.MissionPathBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TrainingPathRes implements Serializable {

	/**
	 * 训练路径
	 */
	@ApiModelProperty(value = "训练路径")
	private List<MissionPathBo> path;

	/**
	 * 全部训练
	 */
	@ApiModelProperty(value = "全部训练")
	private List<RelatedTrainingRes> related;
}
