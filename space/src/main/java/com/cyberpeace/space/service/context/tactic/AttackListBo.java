package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AttackListBo {

	@ApiModelProperty(value = "")
	private String resourceId;

	@ApiModelProperty(value = "")
	private String name;

	@ApiModelProperty(value = "")
	private String tacticId;

	@ApiModelProperty(value = "")
	private String categoryId;

	@ApiModelProperty(value = "")
	private String category;

	@ApiModelProperty(value = "")
	private String description;

	@ApiModelProperty(value = "")
	private String scene;

	@ApiModelProperty(value = "")
	private String feature;
}
