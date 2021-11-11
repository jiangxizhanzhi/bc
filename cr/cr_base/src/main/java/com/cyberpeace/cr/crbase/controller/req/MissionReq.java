package com.cyberpeace.cr.crbase.controller.req;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MissionReq implements Serializable {

	@ApiModelProperty(required = true)
	private String title;

	@ApiModelProperty(required = true)
	private String description;

	@ApiModelProperty(required = true)
	private Integer score;

	@ApiModelProperty(required = true)
	private List<Integer> skillPoints;

	@ApiModelProperty(required = true)
	private String answer;

	@ApiModelProperty(required = true)
	private String option;

	@ApiModelProperty(required = true)
	private Integer type;

	@ApiModelProperty(required = true)
	private String category;

	@ApiModelProperty(required = true)
	private String writeUp;

	@ApiModelProperty(required = true)
	private Integer missionStage;
}
