package com.cyberpeace.cr.teamtrain.service.context;

import java.util.List;

import lombok.Data;

@Data
public class TeamTrainTemplateRole {

	private String resourceId;


	private String name;

	private String description;

	private Integer roleGroup;

	private String missionsScheme;

	private List<String> assetNodes;

	private List<String> children;

	private List<String> visibleNodes;

	private String key;

	private String users;

	private String type;

}