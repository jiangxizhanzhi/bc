package com.cyberpeace.cr.teamtrain.service.context;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队实例角色
 */
@Data
public class RoomRole  {

	@ApiModelProperty(value = "编号")
	private String resourceId;

	@ApiModelProperty(value = "房间id")
	private String trainingTeam;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "任务编排")
	private String missionScheme;

	@ApiModelProperty(value = "用户列表", required = true)
	@NotNull
	private List<String> users;

	@ApiModelProperty(value = "角色组", required = true)
	@NotNull
	private Integer roleGroup;

	@ApiModelProperty(value = "节点")
	private Object nodes;

	@ApiModelProperty(value = "用户数据")
	private Object usersData;

}
