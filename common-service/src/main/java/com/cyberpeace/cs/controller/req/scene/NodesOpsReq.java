package com.cyberpeace.cs.controller.req.scene;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 节点操作请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class NodesOpsReq implements Serializable {

	/**
	 * 中台场景id
	 */
	@ApiModelProperty(value = "场景id")
	private String mpSceneId;

	/**
	 * 节点id
	 */
	@ApiModelProperty(value = "节点ids")
	private List<String> nodeIds;
}
