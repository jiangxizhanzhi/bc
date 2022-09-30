package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneIdReq implements Serializable {

	@ApiModelProperty("场景id")
	private String sceneId;

}
