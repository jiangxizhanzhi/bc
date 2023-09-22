package com.cyberpeace.rtks.controller.res.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RelatedTrainingRes implements Serializable {

	@ApiModelProperty(value = "训练id，用于跳转")
	private String trainingId;

	@ApiModelProperty(value = "训练类型")
	private Integer trainingType;

	@ApiModelProperty(value = "训练难度")
	private Integer difficulty;

	@ApiModelProperty(value = "训练名称")
	private String trainingName;

	@ApiModelProperty(value = "训练状态 (0：未开始，1：已开始， 2：已结束，3：已暂停,4：恢复")
	private Integer trainingStatus;

	@ApiModelProperty(value = "LOGO")
	private String logo;

	@ApiModelProperty(value = "当前用户在训练里的角色")
	private Integer roleGroup;

}
