package com.cyberpeace.oj.rtks.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleAddInput implements Serializable {

	@ApiModelProperty(value = "岗位名称")
	@NotBlank
	private String name;

	@ApiModelProperty(value = "岗位描述")
	private String description;

	@NotBlank
	@ApiModelProperty(value = "岗位所属层级id")
	private String levelId;

	@NotNull
	@ApiModelProperty(value = "技能体系")
	private int category;

}
