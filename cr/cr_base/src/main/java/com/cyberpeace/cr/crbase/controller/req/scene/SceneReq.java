package com.cyberpeace.cr.crbase.controller.req.scene;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneReq implements Serializable {

	@NotNull
	@ApiModelProperty(value = "场景管理器ID")
	private String sceneManagerId;
}
