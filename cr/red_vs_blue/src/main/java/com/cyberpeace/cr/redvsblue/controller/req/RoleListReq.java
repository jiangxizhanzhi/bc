package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.redvsblue.service.context.ColorMission;

import lombok.Data;

@Data
public class RoleListReq<T> implements Serializable {

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

	private ColorMission<T> missionsScheme;

}
