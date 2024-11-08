package com.cyberpeace.cs.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 节点老年 退休金 计划请求体
 *
 * @author SN
 * @date 2024/11/08
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
