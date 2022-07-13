package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionPathPageBo extends MissionTemplateSimple implements Serializable {

	@ApiModelProperty("训练类型， 用于跳转")
	private Integer trainingType;

	@ApiModelProperty("训练id，用于跳转")
	private String trainingId;

	@ApiModelProperty("当前用户在训练中的角色，用于点击跳转")
	private Integer roleGroup;

	@ApiModelProperty("是否有可用场景")
	private Boolean available;

	@ApiModelProperty("类型")
	private Integer type;

	@ApiModelProperty("分数")
	private Integer score;
}
