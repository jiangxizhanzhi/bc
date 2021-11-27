package com.cyberpeace.cs.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneReq implements Serializable {

	@ApiModelProperty(value = "场景管理器ID")
	private String localSceneId;
}
