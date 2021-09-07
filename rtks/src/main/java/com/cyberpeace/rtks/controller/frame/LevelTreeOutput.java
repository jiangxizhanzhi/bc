package com.cyberpeace.rtks.controller.frame;

import java.util.List;

import com.cyberpeace.rtks.controller.role.RoleDetailOutput;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LevelTreeOutput {

	private String resourceId;

	@ApiModelProperty(value = "框架/层级名称")
	private String name;

	@ApiModelProperty(value = "框架/层级描述")
	private String description;

	@ApiModelProperty(value = "子层级列表，不存在则为null")
	private List<LevelTreeOutput> children;

	@ApiModelProperty(value = "岗位列表，不存在则为null")
	private List<RoleDetailOutput> roleDetailList;
}
