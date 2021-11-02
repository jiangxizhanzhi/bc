package com.cyberpeace.cr.teamtrain.model.template;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseInfoReq implements Serializable {

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
	private List<String> pre_skill;

	@ApiModelProperty(required = true)
	@NotNull
	private List<String> train_skill;

	@ApiModelProperty(required = true)
	@NotNull
	private Integer peopleNumber;
}
