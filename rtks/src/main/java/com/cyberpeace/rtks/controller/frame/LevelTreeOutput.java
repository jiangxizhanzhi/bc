package com.cyberpeace.rtks.controller.frame;

import java.util.List;

import com.cyberpeace.rtks.controller.role.RoleDetailOutput;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LevelTreeOutput {

	/**
	 * 框架/层级id
	 */
	@ApiModelProperty(value = "框架/层级id")
	private String resourceId;

	/**
	 * 框架/层级名称
	 */
	@ApiModelProperty(value = "框架/层级名称")
	private String name;

	/**
	 * 框架/层级描述
	 */
	@ApiModelProperty(value = "框架/层级描述")
	private String description;

	/**
	 * 子层级列表，不存在则为null
	 */
	@ApiModelProperty(value = "子层级列表，不存在则为null")
	private List<LevelTreeOutput> children;

	/**
	 * 岗位列表，不存在则为null
	 */
	@ApiModelProperty(value = "岗位列表，不存在则为null")
	private List<RoleDetailOutput> roleDetailList;
}
