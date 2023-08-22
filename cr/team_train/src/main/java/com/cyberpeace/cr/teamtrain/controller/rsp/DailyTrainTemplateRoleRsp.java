package com.cyberpeace.cr.teamtrain.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DailyTrainTemplateRoleRsp implements Serializable {

//	@ApiModelProperty(value = "编号")
//	private String resourceId;
//
//	@ApiModelProperty(value = "名称")
//	private String name;
//
//	@ApiModelProperty(value = "角色id")
//	private String id;
//
//	@ApiModelProperty(value = "描述")
//	private String description;
//
//	@ApiModelProperty(value = "角色组")
//	private Integer roleGroup;
//
//	@ApiModelProperty(value = "任务编排")
//	private ColorMission<AddTemplateMission> missionsScheme;
//
//	@ApiModelProperty(value = "资产节点")
//	private List<CrRoleNode> assetNodes;
//
//	@ApiModelProperty(value = "子角色，冗余")
//	private List<String> children;
//
//	@ApiModelProperty(value = "战争迷雾")
//	private List<CrRoleNode> visibleNodes;
//
//	@ApiModelProperty(value = "KEY（不知道有什么用）")
//	private String key;
//
//	@ApiModelProperty(value = "包含的用户")
//	private String users;
//
//	@ApiModelProperty(value = "类型")
//	private String type;

	@ApiModelProperty(value = "组织名称")
	private String name;

	@ApiModelProperty(value = "场景类型")
	private String type;

	private List<TeamTrainTemplateRoleRsp>  children;
}
