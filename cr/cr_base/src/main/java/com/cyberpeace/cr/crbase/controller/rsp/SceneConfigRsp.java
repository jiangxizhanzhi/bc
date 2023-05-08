package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 场景配置返回值
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class SceneConfigRsp {

	@ApiModelProperty(value = "节点数")
	private Integer nodeCount;

	@ApiModelProperty(value = "是否启动测试场景")
	private Boolean hasScene;
}
