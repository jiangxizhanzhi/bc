package com.cyberpeace.oj.rtks.controller.res;

import com.cyberpeace.oj.rtks.dao.entity.Role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleOutput {

	private String resourceId;

	@ApiModelProperty(value = "岗位名称")
	private String name;

	@ApiModelProperty(value = "岗位描述")
	private String description;

	@ApiModelProperty(value = "岗位所属层级id")
	private String levelId;

	@ApiModelProperty(value = "技能体系")
	private int category;

	public RoleOutput(Role role) {
		this.resourceId = role.getResourceId();
		this.name = role.getName();
		this.description = role.getDescription();
		this.levelId = role.getLevelId();
		this.category = role.getCategory();
	}

}
