package com.cyberpeace.cr.teamtrain.service.context;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队实例角色
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RoomRole {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "编号")
	private String resourceId;

	/**
	 * 训练团队
	 */
	@ApiModelProperty(value = "房间id")
	private String trainingTeam;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 任务计划
	 */
	@ApiModelProperty(value = "任务编排")
	private String missionScheme;

	/**
	 * 用户
	 */
	@ApiModelProperty(value = "用户列表", required = true)
	@NotNull
	private List<String> users;

	/**
	 * 角色组
	 */
	@ApiModelProperty(value = "角色组", required = true)
	@NotNull
	private Integer roleGroup;

	/**
	 * 节点
	 */
	@ApiModelProperty(value = "节点")
	private Object nodes;

	/**
	 * 用户数据
	 */
	@ApiModelProperty(value = "用户数据")
	private Object usersData;

}
