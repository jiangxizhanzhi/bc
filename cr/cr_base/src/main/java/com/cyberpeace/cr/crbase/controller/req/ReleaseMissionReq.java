package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReleaseMissionReq {

	@ApiModelProperty(required = true)
	private String roleResourceId;

	@ApiModelProperty(required = true)
	private String missionResourceId;

}