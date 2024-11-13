package com.cyberpeace.cs.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 节点操作请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class NodeOpsReq implements Serializable {

	/**
	 * 中台场景id
	 */
	@ApiModelProperty(value = "场景id")
	private String mpSceneId;

	/**
	 * 节点id
	 */
	@ApiModelProperty(value = "节点id")
	private String nodeId;
}
