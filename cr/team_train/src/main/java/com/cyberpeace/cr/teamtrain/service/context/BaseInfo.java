package com.cyberpeace.cr.teamtrain.service.context;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseInfo implements Serializable {

	@ApiModelProperty(required = true)
	@NotBlank
	private String name;

	@ApiModelProperty(required = true)
	@NotNull
	private Integer category;

	@ApiModelProperty(required = true)
	@NotNull
	private String description;

	@ApiModelProperty(required = true)
	@NotNull
	private String logo;

	@ApiModelProperty(required = true)
	@NotNull
	private Integer difficult;

	@ApiModelProperty(required = true)
	@NotNull
	private Integer durationTime;

	@ApiModelProperty(required = true)
	@NotNull
	private List<String> preSkill;

	@ApiModelProperty(required = true)
	@NotNull
	private List<String> trainSkill;

	@ApiModelProperty(required = true)
	@NotNull
	private Integer peopleNumber;
}
