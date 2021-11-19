package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReleaseMissionReq {

	@ApiModelProperty(value = "角色id", required = true)
	private String roleResourceId;

	@ApiModelProperty(value = "任务id", required = true)
	private String missionResourceId;

}