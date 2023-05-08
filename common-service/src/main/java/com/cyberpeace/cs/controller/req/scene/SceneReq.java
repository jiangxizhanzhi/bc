package com.cyberpeace.cs.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景要求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class SceneReq implements Serializable {

	@ApiModelProperty(value = "场景管理器ID")
	private String localSceneId;
}
