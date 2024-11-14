package com.cyberpeace.rtks.controller.role;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleAddInput extends RoleInput {

	/**
	 * 岗位名称
	 */
	@ApiModelProperty(value = "岗位名称")
	@NotBlank
	private String name;

	/**
	 * 岗位所属层级id
	 */
	@NotBlank
	@ApiModelProperty(value = "岗位所属层级id")
	private String levelId;

}
