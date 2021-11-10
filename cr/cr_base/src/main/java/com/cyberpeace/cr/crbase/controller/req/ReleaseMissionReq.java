package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReleaseMissionReq {

	@ApiModelProperty(required = true)
	String roleResourceId;

	@ApiModelProperty(required = true)
	String missionResourceId;

}