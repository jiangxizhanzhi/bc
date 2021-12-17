package com.cyberpeace.cr.redvsblue.controller.req;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionConfig {

	@ApiModelProperty(value = "角色id", required = true)
	private String roleResourceId;

	@ApiModelProperty(value = "任务id列表", required = true)
	private List<String> missions;
}
