package com.cyberpeace.cr.crbase.controller.req.scene;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 场景节点操作请求体
 *
 * @author SN
 * @date 2024/11/11
 */

@Data
public class SceneNodesOpsReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "当前业务id", required = true)
	@NotBlank
	private String resourceId;

	/**
	 * 节点id
	 */
	@ApiModelProperty(value = "节点id列表", required = true)
	@NotBlank
	private List<String> nodeIds;
}
