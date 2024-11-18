package com.cyberpeace.cr.teamtrain.service.context;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队实例角色
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class RoomRole {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "编号")
	private String resourceId;

	/**
	 * 训练团队
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "房间id")
	private String trainingTeam;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 任务计划
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "任务编排")
	private String missionScheme;

	/**
	 * 用户
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "用户列表", required = true)
	@NotNull
	private List<String> users;

	/**
	 * 角色组
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "角色组", required = true)
	@NotNull
	private Integer roleGroup;

	/**
	 * 节点
	 *
	 * @see Object
	 */
	@ApiModelProperty(value = "节点")
	private Object nodes;

	/**
	 * 用户数据
	 *
	 * @see Object
	 */
	@ApiModelProperty(value = "用户数据")
	private Object usersData;

}
