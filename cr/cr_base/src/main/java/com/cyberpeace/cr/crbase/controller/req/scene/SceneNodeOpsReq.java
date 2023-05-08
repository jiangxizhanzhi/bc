package com.cyberpeace.cr.crbase.controller.req.scene;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景节点运维请求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class SceneNodeOpsReq implements Serializable {

	@ApiModelProperty(value = "当前业务id", required = true)
	@NotBlank
	private String resourceId;

	@ApiModelProperty(value = "节点id", required = true)
	@NotBlank
	private String nodeId;
}
