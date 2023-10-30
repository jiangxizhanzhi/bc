package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.UserData;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamRoomRoleRes  {

	@ApiModelProperty(value = "编号")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "房间id")
	private String trainingTeam;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "角色组")
	private Integer roleGroup;

	@ApiModelProperty(value = "节点")
	private String nodes;

	@ApiModelProperty(value = "用户列表")
	private List<String> users;

	@ApiModelProperty(value = "用户id列表字符串")
	private String userIds;

	@ApiModelProperty(value = "用户数据")
	private List<UserData> usersData;

	@ApiModelProperty(value = "任务编排")
	private String missionScheme;

}
