package com.cyberpeace.cr.teamtrain.controller.req;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.teamtrain.service.context.MissionColor;

import lombok.Data;

@Data
public class RoleListReq implements Serializable {

	private String Id;

	private String resourceId;

	private String name;

	private String description;

	private Integer roleGroup;

	private List<String> assetNodes;

	private List<String> children;

	private List<String> visibleNodes;

	private String key;

	private String users;

	private String type;

	private MissionColor missionsScheme;

}
