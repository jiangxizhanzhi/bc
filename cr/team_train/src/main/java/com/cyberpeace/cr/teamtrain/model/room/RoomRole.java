package com.cyberpeace.cr.teamtrain.model.room;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoomRole {

	private String resourceId;

	private String trainingTeam;

	private String name;

	private String description;

	private String missionScheme;

	@ApiModelProperty(required = true)
	@NotNull
	private List<String> users;

	@ApiModelProperty(required = true)
	@NotNull
	private Integer roleGroup;

	private Object nodes;

	private Object usersData;

}
