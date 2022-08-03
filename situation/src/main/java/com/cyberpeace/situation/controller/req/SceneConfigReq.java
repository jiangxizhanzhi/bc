package com.cyberpeace.situation.controller.req;

import javax.validation.constraints.NotNull;

import com.cyberpeace.situation.service.context.SceneConfigContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneConfigReq extends SceneConfigContext {

	@ApiModelProperty("场景id")
	@NotNull
	private String sceneId;

	@ApiModelProperty("是否为实例场景")
	private Boolean isInstance;

}