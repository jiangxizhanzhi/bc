package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.teamtrain.service.context.ColorMission;

import lombok.Data;

@Data
public class TeamTrainTemplateRoleRsp {

	private String resourceId;

	private String name;

	private String description;

	private Integer roleGroup;

	private ColorMission missionsScheme;

	private List<String> assetNodes;

	private List<String> children;

	private List<String> visibleNodes;

	private String key;

	private String users;

	private String type;

}
