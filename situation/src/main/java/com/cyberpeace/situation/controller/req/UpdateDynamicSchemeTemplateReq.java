package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateDynamicSchemeTemplateReq extends AddDynamicSchemeTemplateReq{

	@ApiModelProperty("资源id")
	private String resourceId;
}
