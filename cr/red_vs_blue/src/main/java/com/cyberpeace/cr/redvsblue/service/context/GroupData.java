package com.cyberpeace.cr.redvsblue.service.context;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GroupData {

	@ApiModelProperty(value = "")
	private String group;

	@ApiModelProperty(value = "")
	private Integer groupNumber;

	@ApiModelProperty(value = "")
	private Integer groupHash;

	@ApiModelProperty(value = "")
	private List<GroupRole> roles;

	@ApiModelProperty(value = "")
	private Integer peopleNumber;

	@ApiModelProperty(value = "")
	private Integer missionNumber;

}
