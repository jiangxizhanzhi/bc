package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class RbTemplateRoleRsp {

	private String resourceId;

	private String name;

	private String description;

	private Integer roleGroup;

	private JSONObject missionsScheme;

	private List<String> assetNodes;

	private List<String> children;

	private List<String> visibleNodes;

	private String key;

	private String users;

	private String type;

}
