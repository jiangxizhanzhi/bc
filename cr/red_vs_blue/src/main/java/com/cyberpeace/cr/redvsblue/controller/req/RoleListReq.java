package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.context.role.CrRoleNode;
import com.cyberpeace.cr.crbase.service.context.ColorMission;

import lombok.Data;

@Data
public class RoleListReq<T> implements Serializable {

	private String Id;

	private String resourceId;

	private String name;

	private String description;

	private Integer roleGroup;

	private List<CrRoleNode> assetNodes;

	private List<String> children;

	private List<CrRoleNode> visibleNodes;

	private String key;

	private String users;

	private String type;

	private ColorMission<T> missionsScheme;

}
