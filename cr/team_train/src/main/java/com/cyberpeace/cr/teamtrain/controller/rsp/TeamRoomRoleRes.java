package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.UserData;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队房间岗位结果
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class TeamRoomRoleRes  {

	/**
	 * 编号
	 */
	@ApiModelProperty(value = "编号")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 房间id
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
	 * 用户列表
	 */
	@ApiModelProperty(value = "用户列表")
	private List<String> users;

	/**
	 * 用户id列表字符串
	 */
	@ApiModelProperty(value = "用户id列表字符串")
	private String userIds;

	/**
	 * 用户数据
	 */
	@ApiModelProperty(value = "用户数据")
	private List<UserData> usersData;

	/**
	 * 任务编排
	 */
	@ApiModelProperty(value = "任务编排")
	private String missionScheme;

}
