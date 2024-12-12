package com.cyberpeace.cs.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 场景节点请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class SceneNodeReq implements Serializable {

	/**
	 * 当地场景id
	 */
	@ApiModelProperty(value = "场景管理器ID")
	private String localSceneId;

	/**
	 * 节点id
	 */
	@ApiModelProperty(value = "节点id")
	private String nodeId;

}