package com.cyberpeace.rtks.controller.role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleAddInput extends RoleInput {

	@ApiModelProperty(value = "岗位名称")
	@NotBlank
	private String name;

	@NotBlank
	@ApiModelProperty(value = "岗位所属层级id")
	private String levelId;

	@NotNull
	@ApiModelProperty(value = "技能体系")
	private String category;

}
