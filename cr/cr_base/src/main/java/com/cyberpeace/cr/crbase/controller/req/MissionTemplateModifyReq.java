package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionTemplateModifyReq extends MissionTemplateReq {

	@ApiModelProperty(required = true)
	private String resourceId;

}
