package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionModifyReq extends MissionReq {

	@ApiModelProperty(required = true)
	private String resourceId;

}
