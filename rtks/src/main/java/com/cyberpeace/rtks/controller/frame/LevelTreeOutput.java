package com.cyberpeace.rtks.controller.frame;

import java.util.List;

import com.cyberpeace.rtks.controller.role.RoleDetailOutput;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Level Tree 输出
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class LevelTreeOutput {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "框架/层级id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "框架/层级名称")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "框架/层级描述")
	private String description;

	/**
	 * 子级
	 */
	@ApiModelProperty(value = "子层级列表，不存在则为null")
	private List<LevelTreeOutput> children;

	/**
	 * 角色详情列表
	 */
	@ApiModelProperty(value = "岗位列表，不存在则为null")
	private List<RoleDetailOutput> roleDetailList;
}
