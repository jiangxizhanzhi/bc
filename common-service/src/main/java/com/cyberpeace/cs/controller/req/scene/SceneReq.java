package com.cyberpeace.cs.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneReq implements Serializable {

	/**
	 * 当地场景id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "场景管理器ID")
	private String localSceneId;
}
