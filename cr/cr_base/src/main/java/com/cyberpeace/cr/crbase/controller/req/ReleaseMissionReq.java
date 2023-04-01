package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReleaseMissionReq implements Serializable {

	@ApiModelProperty(value = "角色id", required = true)
	private String roleResourceId;

	@ApiModelProperty(value = "任务id", required = true)
	private String missionResourceId;

}