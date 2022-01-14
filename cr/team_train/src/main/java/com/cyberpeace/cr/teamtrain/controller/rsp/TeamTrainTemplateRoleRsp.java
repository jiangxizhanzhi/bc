package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.AddTemplateMission;
import com.cyberpeace.cr.crbase.service.context.ColorMission;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;

import lombok.Data;

@Data
public class TeamTrainTemplateRoleRsp implements Serializable {

	private String resourceId;

	private String name;

	private String id;

	private String description;

	private Integer roleGroup;

	private ColorMission<AddTemplateMission> missionsScheme;

	private List<CrRoleNode> assetNodes;

	private List<String> children;

	private List<CrRoleNode> visibleNodes;

	private String key;

	private String users;

	private String type;

}
