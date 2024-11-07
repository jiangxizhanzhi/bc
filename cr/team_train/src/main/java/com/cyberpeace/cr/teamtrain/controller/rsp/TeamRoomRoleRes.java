package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.UserData;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队房间角色结果
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class TeamRoomRoleRes implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "编号")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 训练团队
	 */
	@ApiModelProperty(value = "房间id")
	private String trainingTeam;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 角色组
	 */
	@ApiModelProperty(value = "角色组")
	private Integer roleGroup;

	/**
	 * 节点
	 */
	@ApiModelProperty(value = "节点")
	private String nodes;

	/**
	 * 用户
	 */
	@ApiModelProperty(value = "用户列表")
	private List<String> users;

	/**
	 * 用户id列表
	 */
	@ApiModelProperty(value = "用户id列表字符串")
	private String userIds;

	/**
	 * 用户数据
	 */
	@ApiModelProperty(value = "用户数据")
	private List<UserData> usersData;

	/**
	 * 任务计划
	 */
	@ApiModelProperty(value = "任务编排")
	private String missionScheme;

}
