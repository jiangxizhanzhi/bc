package com.cyberpeace.cs.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景节点要求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class SceneNodeReq implements Serializable {

	@ApiModelProperty(value = "场景管理器ID")
	private String localSceneId;

	@ApiModelProperty(value = "节点id")
	private String nodeId;

}