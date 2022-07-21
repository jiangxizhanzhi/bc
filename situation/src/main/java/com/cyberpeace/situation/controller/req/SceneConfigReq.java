package com.cyberpeace.situation.controller.req;

import com.cyberpeace.situation.service.context.SceneConfigContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneConfigReq extends SceneConfigContext {

	@ApiModelProperty("场景id")
	private String sceneId;

}