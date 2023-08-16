package com.cyberpeace.cs.controller.req.scene;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class NodeOpsReq implements Serializable {

	@ApiModelProperty(value = "场景id")
	private String mpSceneId;

	@ApiModelProperty(value = "节点id")
	private String nodeId;
}
