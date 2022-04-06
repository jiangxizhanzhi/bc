package com.cyberpeace.cr.hld.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class HldSceneOpsReq implements Serializable {

	@ApiModelProperty(value = "HLD拓扑id", required = true)
	private String resourceId;

	 @ApiModelProperty(value = "HLD用户id", required = false)
	 private String userId = "task";

}
