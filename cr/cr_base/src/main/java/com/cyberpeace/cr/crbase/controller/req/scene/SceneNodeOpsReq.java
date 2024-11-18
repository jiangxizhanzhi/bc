package com.cyberpeace.cr.crbase.controller.req.scene;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景节点操作 计划请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneNodeOpsReq implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "当前业务id", required = true)
	@NotBlank
	private String resourceId;

	/**
	 * 节点id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "节点id", required = true)
	@NotBlank
	private String nodeId;
}
