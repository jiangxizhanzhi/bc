package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SceneConfigRsp {

	@ApiModelProperty(value = "节点数")
	private Integer nodeCount;

	@ApiModelProperty(value = "是否启动测试场景")
	private Boolean hasScene;
}
