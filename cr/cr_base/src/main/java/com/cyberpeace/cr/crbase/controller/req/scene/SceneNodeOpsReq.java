package com.cyberpeace.cr.crbase.controller.req.scene;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景节点老年 退休金 计划请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class SceneNodeOpsReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "当前业务id", required = true)
	@NotBlank
	private String resourceId;

	/**
	 * 节点id
	 */
	@ApiModelProperty(value = "节点id", required = true)
	@NotBlank
	private String nodeId;
}
