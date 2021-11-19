package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MissionTemplateModifyReq extends MissionTemplateReq {

	@ApiModelProperty(value = "任务id", required = true)
	private String resourceId;

}
