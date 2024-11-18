package com.cyberpeace.cs.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景节点请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneNodeReq implements Serializable {

	/**
	 * 当地场景id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "场景管理器ID")
	private String localSceneId;

	/**
	 * 节点id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "节点id")
	private String nodeId;

}